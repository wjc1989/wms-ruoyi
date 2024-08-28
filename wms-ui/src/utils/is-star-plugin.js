// 用于检测User  star 了Warehouse，e.g.果没有 star，将弹窗InfoUser  star 。
import { Message, MessageBox } from 'element-ui'
// 使用axios代替$.ajax
import axios from 'axios'
// 应用Params
const client_id = '9addfce3712d04898b5a3dbb223db38b8d6495d2e66d07e3c0af71a708ee3b54'
const client_secret = '1f73096ce60406eba8fb297a16245eadf2777540abdf531266b406b2479e25fe'
import store from '@/store'

// 检查 Successful后，多少天No 再检查
const allowDisparity = 1000 * 60 * 60 * 24 * 7

/**
 * 判断当前已 star
 * @param owner Warehouse空间Address(企业、组织 Or个人的Addresspath)
 * @param repo  Warehouse路径(path)
 * @param userId
 * @param redirectUrl
 * @param productName 开Source 项目名
 * @param productLink 开Source 链接
 */
export function isStarRepo(owner, repo, userId, redirectUrl,productName,productLink) {
  const judge = process.env.VUE_APP_JUDGE_STAR
  if (['wms','admin'].includes(store.getters.name)) {
    return true;
  }
  if (!judge || judge == 0) {
    return true;
  }
  const key = userId + '_' + owner + '_' + repo
  // 判断近期已经判断过了
  try {
    if (typeof window !== 'undefined') {
      const isStarRepo = localStorage.getItem(key)
      if (isStarRepo) {
        //  Record star 的 Time， And 当前 Time的差距
        const disparity = new Date().getTime() - parseInt(isStarRepo)

        // 差距小于一月，No 再检测，大于一月，再检测一下
        if (disparity < allowDisparity) {
          console.log('checked ...')
          return true;
        }
      }
    }
  } catch (e) {
    console.error(e)
  }
  return getCode(owner, repo, key, redirectUrl,productName,productLink)
}

// 去请求Authorize
function getCode(owner, repo, key, redirectUrl,productName,productLink) {
  // 检查url中有code
  const code = getParam('code')
  if (code) {
    // 有 code，进一步去请求 access_token
    getAccessToken(code, redirectUrl, owner, repo, key,productName,productLink)
  } else {
    // No 存在code，弹窗Info询问
    confirmStar(redirectUrl,productName,productLink)
    return false;
  }
}

// 弹窗Info点 star
function confirmStar(redirectUrl,productName,productLink) {

  // 弹窗Info文字
  const tipStr = `
		<div>
			<p><b>嗨，同学，来支持一下 ${productName} 吧，项目点个 star ！</b></p>
			<div>仅需两步即可完成：<br>
				<div>1、打开 ${productName} <a href=${productLink} target="_blank" style="color: red">${productName}</a>，在右上角点个 star 。</div>
				<div>2、点击下方 [ 同意Authorize检测 ] Button，同意 ${productName}  获取 API Prower进行检测。</div>
			</div>
			<p><b>本页面将在 star 后正常开放展示</b></p>
		</div>
		`
    // 弹窗Info
    MessageBox.alert(tipStr, 'Info', {
      // if you want to disable its autofocus
      // autofocus: false,
      confirmButtonText: '同意Authorize检测',
      showClose: false,
      dangerouslyUseHTMLString: true,
      callback: (action) => {
        if (action === 'confirm') {
          // User 点了OK，去 gitee 官方请求Authorize获取
          goAuth(redirectUrl)
        }
      }
    })
}

function toStar(redirectUrl,productName,productLink,accessToken,owner,repo,key,code) {

  // 弹窗Info文字
  const tipStr = `
		<div>
			<p><b>嗨，同学，来支持一下 ${productName} 吧，项目点个 star ！</b></p>
			<div>Click【去Star】Button。进入${productName} 开Source 项目主页，在右上角点个 star 。</div>
			<p><b>本页面将在 star 后正常开放展示</b></p>
		</div>
		`
    MessageBox.confirm(tipStr, 'Info', {
      // if you want to disable its autofocus
      // autofocus: false,
      closeOnClickModal: false,
      confirmButtonText: '去Star',
      cancelButtonText: '我已Star',
      showClose: false,
      dangerouslyUseHTMLString: true,
      callback: (action) => {
        if (action === 'confirm') {
          // User 点了OK，去 gitee 官方请求Authorize获取
          window.open(productLink)
          toStar(redirectUrl,productName,productLink,accessToken,owner,repo,key,code)
        }
        if (action === 'cancel') {
          //检测
          judgeStar(accessToken,owner,repo,key,productName,productLink,redirectUrl,code)
        }
      }
    })
}

// 跳转到 gitee Authorize界面
function goAuth(redirectUrl) {
  location.href = 'https://gitee.com/oauth/authorize' +
    '?client_id=' + client_id +
    '&redirect_uri=' + redirectUrl +
    '&response_type=code' +
    '&scope=user_info'
}

// 获取 access_token
function getAccessToken(code, redirectUrl, owner, repo, key,productName,productLink) {
  // 根据 code 获取 access_token
  axios.post('https://gitee.com/oauth/token', {
    grant_type: 'authorization_code',
    code: code,
    client_id: client_id,
    redirect_uri: redirectUrl,
    client_secret: client_secret
  })
    .then(res => {
      // 拿到 access_token
      const accessToken = res.data.access_token
      judgeStar(accessToken,owner, repo, key,productName,productLink,redirectUrl,code)
    })
    .catch(e => {
      console.log('请求错误 ', e)
      // e.g.果Request有错，可能Yes服务器宕机了，暂停一天检测
      if (e.response && (e.response.status === 0 || e.response.status === 502)) {
        // 一天内No 再检查
        const ygTime = allowDisparity - (1000 * 60 * 60 * 24)
        if (typeof window !== 'undefined') {
          localStorage.setItem(key, new Date().getTime() - ygTime)
        }
        // Refresh url，去掉 code Params
        location.href = location.href.replace('?code=' + code, '')
      }

      // 无效Authorize，可能Yes code 无效
      const errorMsg = (e.response && e.response.data.error) || JSON.stringify(e)
      if (errorMsg === 'invalid_grant') {
        console.log('无效code', code)

        let url = location.href.replace('?code=' + code, '')
        url = url.replace('&code=' + code, '')
        location.href = url
      }
    })
}


function judgeStar(accessToken,owner, repo, key,productName,productLink,redirectUrl,code){
  // 根据 access_token 判断 star 了Warehouse
  axios.get(`https://gitee.com/api/v5/user/starred/${owner}/${repo}`, {
    params: { access_token: accessToken }
  })
    .then(res => {
      // success 回调即代表已经 star，gitee API 请求体No Back任何Data
      console.log('-> stared ...')
      //  Record本次检查 Time
      if (typeof window !== 'undefined') {
        localStorage.setItem(key, new Date().getTime())
      }
      Message.success('感谢你的支持  ❤️ ❤️ ❤️ ，我们将努力变得更加完善！')
      setTimeout(()=>{
        location.href = location.href.replace('?code=' + code, '')
      },1000)
    })
    .catch(e => {
      // console.log('ff请求错误 ', e);
      // e.g.下Back，代表没有 star
      if (e.response && e.response.status === 404) {
        console.log('not star ...')
        toStar(redirectUrl,productName,productLink,accessToken,owner,repo,key,code);
      }
    })
}

// 获取 url 携带的Params
function getParam(name) {
  const urls = window.location.href.split('?')
  if (urls.length < 2) {
    return null
  }
  let url = urls[1]
  let obj = {} // 声明Params对象
  let arr = url.split('&') // 以&符No.分割数组
  for (let i = 0; i < arr.length; i++) {
    let arrNew = arr[i].split('=') // 以"="分割数组
    obj[arrNew[0]] = arrNew[1]
  }

  return obj[name]
}

