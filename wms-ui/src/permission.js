import router from './router'
import store from './store'
import { Message } from 'element-ui'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'
import { getToken } from '@/utils/auth'
import { isRelogin } from '@/utils/request'

NProgress.configure({ showSpinner: false })

const whiteList = ['/login', '/auth-redirect', '/bind', '/register']

router.beforeEach((to, from, next) => {
  NProgress.start()
  if (window._hmt) {
    if (to.path) {
      window._hmt.push(['_trackPageview', '/#' + to.fullPath]);
    }
  }
  if (getToken()) {
    to.meta.title && store.dispatch('settings/setTitle', to.meta.title)
    /* has token*/
    if (to.path === '/login') {
      next({ path: '/' })
      NProgress.done()
    } else {
      //当页面Refresh，vuex里面没值，请求重新获取,Data会重新初始化
      if (store.getters.roles.length === 0) {
        isRelogin.show = true
        store.dispatch("wms/getWarehouseList");
        store.dispatch("wms/getAreaList");
        store.dispatch("wms/getRackList");
        store.dispatch('wms/getSuppliers')
        store.dispatch('wms/getCustomer')
        store.dispatch('wms/getCarrier')
        // 判断当前User 已拉取完user_info Info
        store.dispatch('GetInfo').then(() => {
          isRelogin.show = false
          store.dispatch('GenerateRoutes').then(accessRoutes => {
            // 根据rolesPrower生成可Router表
            router.addRoutes(accessRoutes) // 动态Add可访问Router表
            next({ ...to, replace: true }) // hack方法 确保addRoutes已完成
          })
        }).catch(err => {
            store.dispatch('LogOut').then(() => {
              Message.error(err)
              next({ path: '/' })
            })
          })
      } else {
        next()
      }
    }
  } else {
    // 没有token
    if (whiteList.indexOf(to.path) !== -1) {
      // 在免Login白名，直接进入
      next()
    } else {
      next(`/login?redirect=${to.fullPath}`) // No则All重定向到Login页
      NProgress.done()
    }
  }
})

router.afterEach(() => {
  NProgress.done()
})
