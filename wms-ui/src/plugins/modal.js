import { Message, MessageBox, Notification, Loading } from 'element-ui'

let loadingInstance;

export default {
  // 消息Info
  msg(content) {
    Message.info(content)
  },
  // 错误消息
  msgError(content) {
    Message.error(content)
  },
  //  Successful消息
  msgSuccess(content) {
    Message.success(content)
  },
  // Warning消息
  msgWarning(content) {
    Message.warning(content)
  },
  // 弹出Info
  alert(content) {
    MessageBox.alert(content, "Info")
  },
  // 错误Info
  alertError(content) {
    MessageBox.alert(content, "Info", { type: 'error' })
  },
  //  SuccessfulInfo
  alertSuccess(content) {
    MessageBox.alert(content, "Info", { type: 'success' })
  },
  // WarningInfo
  alertWarning(content) {
    MessageBox.alert(content, "Info", { type: 'warning' })
  },
  // 通知Info
  notify(content) {
    Notification.info(content)
  },
  // 错误通知
  notifyError(content) {
    Notification.error(content);
  },
  //  Successful通知
  notifySuccess(content) {
    Notification.success(content)
  },
  // Warning通知
  notifyWarning(content) {
    Notification.warning(content)
  },
  // OK窗体
  confirm(content) {
    return MessageBox.confirm(content, "Info", {
      confirmButtonText: 'OK',
      cancelButtonText: 'Cancel',
      type: "warning",
    })
  },
  // 提交Content
  prompt(content) {
    return MessageBox.prompt(content, "Info", {
      confirmButtonText: 'OK',
      cancelButtonText: 'Cancel',
      type: "warning",
    })
  },
  // 打开遮罩层
  loading(content) {
    loadingInstance = Loading.service({
      lock: true,
      text: content,
      spinner: "el-icon-loading",
      background: "rgba(0, 0, 0, 0.7)",
    })
  },
  // Close遮罩层
  closeLoading() {
    loadingInstance.close();
  }
}
