/**
 * @Description
 * @Version
 * @Author  徐泽鹏
 * @date 2023/5/23 11:49
 */
export default class Socket {
  // 当前websocket的url
  url;
  // 重连时间配置，超时时间配置
  // resetTime 重连时间  timeout 超时处理时间
  options = { resetTime: 60000, timeout: 10000 };
  // 是否自动关闭
  customClose = false;
  // promise存储池
  promisePool;
  // websocket实例
  _websocket;
  constructor(options = {}) {
    this.url = "ws://127.0.0.1:37989";
    this.options = { ...this.options, ...options };
    // close来源判断及后续操作
    this.customClose = false;
    this.promisePool = {};
  }
  // 判断返回数据是否json数据
  isJSON(str) {
    if (typeof str == "string") {
      try {
        return JSON.parse(str);
      } catch (e) {
        return false;
      }
    } else {
      return false;
    }
  }
  // 关闭回调处理
  closeCallback() {
    // 非手动关闭连接时重连
    if (!this.customClose) {
      // 对应的重连操作
      this.close(false);
      this._websocket = undefined;
      const timer = setTimeout(() => {
        this.open();
        clearTimeout(timer);
      }, this.options.resetTime);
      // 若手动close，恢复初始状态
      this.customClose = false;
    }
  }

  /**
   * 打开 WebSocket 连接并返回一个解析为 WebSocket 实例的 Promise。
   *
   * @param {function} openChange - WebSocket 连接打开时要调用的回调函数。
   * @param {function} onMessageCallback - 接收到消息时要调用的回调函数。
   * @return {Promise} 一个解析为 WebSocket 实例的 Promise。
   */
  open(openChange, onMessageCallback) {
    return new Promise((resolve) => {
      if (typeof this._websocket === "undefined") {
        this._websocket = new WebSocket(this.url);
        this._websocket.onopen = (e) => {
          console.log(openChange, "open---openChange");
          if (openChange) {
            openChange(true);
          }
          resolve({ e, ws: this });
        };
        this._websocket.onerror = () => {
          if (openChange) {
            openChange(false);
          }
          this.closeCallback();
        };
        this._websocket.onclose = () => {
          if (openChange) {
            openChange(false);
          }
          this.closeCallback();
        };
        this._websocket.onmessage = (e) => {
          const msg = this.isJSON(e.data) ? JSON.parse(e.data) : e.data;
          console.log("msg", msg);
          // 消息事件的回调
          if (onMessageCallback) {
            onMessageCallback(msg);
          }
          const req = this.promisePool[msg.apiName];
          console.log("req", req);
          if (
            req &&
            msg?.apiName &&
            String(msg?.resultAck?.info || "").includes("commitJob ok")
          ) {
            return false;
          } else {
            req && req.resolve(msg);
            req && clearTimeout(req.timeoutCallback);
            delete this.promisePool[msg.apiName];
          }
        };
      }
    });
  }
  // 关闭websocket链接 closing 为true时为手动关闭
  close(closing = true) {
    this.customClose = closing;
    this._websocket &&
      this._websocket.readyState === 1 &&
      this._websocket.close();
  }
  // content中为发送到ws所有数据
  send(content, timeout = null) {
    // 记录当次请求时的时间戳，便于检验超时情况
    const timestamp = new Date().getTime();
    // 如果 this.options.timeout 秒后ws无消息返回
    const timeoutCallback = setTimeout(
      () => {
        const req = this.promisePool[content.apiName];
        if (req && req.timestamp === timestamp) {
          req.resolve({
            apiName: content.apiName,
            resultAck: { errorCode: 22 },
            Error: "打印超时",
          });
          clearTimeout(req.timeoutCallback);
          delete this.promisePool[content.apiName];
        }
      },
      timeout !== null ? timeout : this.options.timeout
    );
    return new Promise((resolve, reject) => {
      this.promisePool[content.apiName] = {
        timestamp,
        content,
        resolve,
        reject,
        timeoutCallback,
      };
      console.log("_websocket", this._websocket);
      console.log("_websocket.readyState ", this._websocket.readyState);
      console.log("...content ", JSON.stringify({ ...content }));
      this._websocket && this._websocket.readyState === 1
        ? this._websocket.send(JSON.stringify({ ...content }))
        : this.promisePool[content.apiName].resolve({
            apiName: content.apiName,
            resultAck: { errorCode: 22 },
            Error: "打印超时",
          });
    });
  }
  // 自定义消息发送
  customSend(content) {
    this._websocket &&
      this._websocket.readyState === 1 &&
      this._websocket.send(JSON.stringify({ ...content }));
  }
}
