import axios from 'axios'
import {Notification, MessageBox, Message, Loading} from 'element-ui'
import store from '@/store'
import {getToken} from '@/utils/auth'
import errorCode from '@/utils/errorCode'
import {tansParams, blobValidate} from "@/utils/ruoyi";
import cache from '@/plugins/cache'
import {saveAs} from 'file-saver'
import InsufficientStockAlert from "@/utils/wms";

let downloadLoadingInstance;
// Show重新Login
export let isRelogin = {show: false};

axios.defaults.headers['Content-Type'] = 'application/json;charset=utf-8'
// Createaxios实例
const service = axios.create({
  // axios中请求配置有baseURL选项，表示请求URL公共部分
  baseURL: process.env.VUE_APP_BASE_API,
  // 超时
  timeout: 10000
})

// request拦截器
service.interceptors.request.use(config => {
  // 需要设置 token
  const isToken = (config.headers || {}).isToken === false
  // 需要防止Data重复提交
  const isRepeatSubmit = (config.headers || {}).repeatSubmit === false
  if (getToken() && !isToken) {
    config.headers['Authorization'] = 'Bearer ' + getToken() // 让每个请求携带Custom 义token 请根据Real 情况自行Modify
  }
  // get请求映射paramsParams
  if (config.method === 'get' && config.params) {
    let url = config.url + '?' + tansParams(config.params);
    url = url.slice(0, -1);
    config.params = {};
    config.url = url;
  }
  if (!isRepeatSubmit && (config.method === 'post' || config.method === 'put')) {
    const requestObj = {
      url: config.url,
      data: typeof config.data === 'object' ? JSON.stringify(config.data) : config.data,
      time: new Date().getTime()
    }
    const sessionObj = cache.session.getJSON('sessionObj')
    if (sessionObj === undefined || sessionObj === null || sessionObj === '') {
      cache.session.setJSON('sessionObj', requestObj)
    } else {
      const s_url = sessionObj.url;                  // Request
      const s_data = sessionObj.data;                // 请求Data
      const s_time = sessionObj.time;                // 请求 Time
      const interval = 1000;                         // 间隔 Time(ms)，小于此 Time视重复提交
      if (s_data === requestObj.data && requestObj.time - s_time < interval && s_url === requestObj.url) {
        const message = 'Data is being processed,please do not resubmit';
        console.warn(`[${s_url}]: ` + message)
        return Promise.reject(new Error(message))
      } else {
        cache.session.setJSON('sessionObj', requestObj)
      }
    }
  }
  return config
}, error => {
  console.log(error)
  Promise.reject(error)
})

// 响应拦截器
service.interceptors.response.use(res => {
    // 未设置Status码则默认 SuccessfulStatus
    const code = res.data.code || 200;
    // 获取错误 Info
    const msg = errorCode[code] || res.data.msg || errorCode['default']
    // 二进制Data则直接Back
    if (res.request.responseType === 'blob' || res.request.responseType === 'arraybuffer') {
      return res.data
    }
    if (code === 401) {
      if (!isRelogin.show) {
        isRelogin.show = true;
        MessageBox.confirm('Session timeout，Please relogin。', 'Info', {
            confirmButtonText: 'Relogin',
            cancelButtonText: 'Cancel',
            type: 'warning'
          }
        ).then(() => {
          isRelogin.show = false;
          store.dispatch('LogOut').then(() => {
            location.href = '/index';
          })
        }).catch(() => {
          isRelogin.show = false;
        });
      }
      return Promise.reject('Session timeout，Please relogin。')
    } else if (code === 500) {
      Message({
        message: msg,
        type: 'error'
      })
      return Promise.reject(new Error(msg))
    } else if (code === 398) {
      // InventoryNo 足
      InsufficientStockAlert(res.data)
      return Promise.reject('error')
    } else if (code === 399) {
      // throw new ServiceException("xx", HttpStatus.CONFIRMATION);
      // 需要User OK的错误
      MessageBox.alert(msg, 'Info', {
        dangerouslyUseHTMLString: true
      }).then(r => {
      });
      return Promise.reject('error')
    } else if (code !== 200) {

      Notification.error({
        title: msg
      })
      return Promise.reject('error')
    } else {
      return res.data
    }
  },
  error => {
    console.log('err' + error)
    let {message} = error;
    if (message == "Network Error") {
      message = "Network Error";
    } else if (message.includes("timeout")) {
      message = "API Timeout";
    } else if (message.includes("Request failed with status code")) {
      message = "API " + message.substr(message.length - 3) + " Exception";
    }
    Message({
      message: message,
      type: 'error',
      duration: 5 * 1000
    })
    return Promise.reject(error)
  }
)

// 通用下载方法
export function download(url, params, filename) {
  downloadLoadingInstance = Loading.service({
    text: "Loading...",
    spinner: "el-icon-loading",
    background: "rgba(0, 0, 0, 0.7)",
  })
  return service.post(url, params, {
    transformRequest: [(params) => {
      return tansParams(params)
    }],
    headers: {'Content-Type': 'application/x-www-form-urlencoded'},
    responseType: 'blob'
  }).then(async (data) => {
    const isLogin = await blobValidate(data);
    if (isLogin) {
      const blob = new Blob([data])
      saveAs(blob, filename)
    } else {
      const resText = await data.text();
      const rspObj = JSON.parse(resText);
      const errMsg = errorCode[rspObj.code] || rspObj.msg || errorCode['default']
      Message.error(errMsg);
    }
    downloadLoadingInstance.close();
  }).catch((r) => {
    console.error(r)
    Message.error('Download error！')
    downloadLoadingInstance.close();
  })
}

export default service
