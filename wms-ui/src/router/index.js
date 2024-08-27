import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/**
 * Note: Router配置项
 *
 * hidden: true                     // 当设置 true 的时候该RouterNo 会再侧边栏出现 e.g.401，login等页面， Or者e.g.一些编辑页面/edit/1
 * alwaysShow: true                 // 当你一个Router下面的 children 声明的Router大于1个时，Auto会变成嵌套的模式--e.g.组件页面
 *                                  // 只有一个时，会将那个子Router当做根RouterShow在侧边栏--e.g.引导页面
 *                                  // 若你想No 管Router下面的 children 声明的个数都Show你的根Router
 *                                  // 你可以设置 alwaysShow: true，这样它就会忽略之前定义的规则，一直Show根Router
 * redirect: noRedirect             // 当设置 noRedirect 的时候该Router在面包屑导航中No 可被点击
 * name:'router-name'               // 设定Router的名字，一定要填写No 然使用<keep-alive>时会出现各种问题
 * query: '{"id": 1, "name": "ry"}' // 访问Router的默认传递Params
 * roles: ['admin', 'common']       // 访问Router的RolePrower
 * permissions: ['a:a:a', 'b:b:b']  // 访问Router的Menu Prower
 * meta : {
    noCache: true                   // e.g.果设置true，则No 会被 <keep-alive> Cache(默认 false)
    title: 'title'                  // 设置该Router在侧边栏 And 面包屑中展示的名字
    icon: 'svg-name'                // 设置该Router的Icon，对应路径src/assets/icons/svg
    breadcrumb: false               // e.g.果设置false，则No 会在breadcrumb面包屑中Show
    activeMenu: '/system/user'      // 当Router设置了该属性，则会高亮相对应的侧边栏。
  }
 */

// 公共Router
export const constantRoutes = [
  {
    path: '/redirect',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/redirect/:path(.*)',
        component: () => import('@/views/redirect')
      }
    ]
  },
  {
    path: '/login',
    component: () => import('@/views/login'),
    hidden: true
  },
  {
    path: '/register',
    component: () => import('@/views/register'),
    hidden: true
  },
  {
    path: '/404',
    component: () => import('@/views/error/404'),
    hidden: true
  },
  {
    path: '/401',
    component: () => import('@/views/error/401'),
    hidden: true
  },
  {
    path: '',
    component: Layout,
    redirect: 'index',
    children: [
      {
        path: 'index',
        component: () => import('@/views/index'),
        name: 'Index',
        meta: { title: 'Index', icon: 'dashboard', affix: true }
      }
    ]
  },
  {
    path: '/user',
    component: Layout,
    hidden: true,
    redirect: 'noredirect',
    children: [
      {
        path: 'profile',
        component: () => import('@/views/system/user/profile/index'),
        name: 'Profile',
        meta: { title: 'Profile', icon: 'user' }
      }
    ]
  }
]

// 动态Router，基于User Prower动态去加载
export const dynamicRoutes = [
  {
    path: '/system/user-auth',
    component: Layout,
    hidden: true,
    permissions: ['system:user:edit'],
    children: [
      {
        path: 'role/:userId(\\d+)',
        component: () => import('@/views/system/user/authRole'),
        name: 'AuthRole',
        meta: { title: ' Distribute Role', activeMenu: '/system/user' }
      }
    ]
  },
  {
    path: '/system/role-auth',
    component: Layout,
    hidden: true,
    permissions: ['system:role:edit'],
    children: [
      {
        path: 'user/:roleId(\\d+)',
        component: () => import('@/views/system/role/authUser'),
        name: 'AuthUser',
        meta: { title: ' Distribute User ', activeMenu: '/system/role' }
      }
    ]
  },
  {
    path: '/system/dict-data',
    component: Layout,
    hidden: true,
    permissions: ['system:dict:list'],
    children: [
      {
        path: 'index/:dictId(\\d+)',
        component: () => import('@/views/system/dict/data'),
        name: 'Data',
        meta: { title: 'DictData', activeMenu: '/system/dict' }
      }
    ]
  },
  {
    path: '/monitor/job-log',
    component: Layout,
    hidden: true,
    permissions: ['monitor:job:list'],
    children: [
      {
        path: 'index',
        component: () => import('@/views/monitor/job/log'),
        name: 'JobLog',
        meta: { title: '调度Logs', activeMenu: '/monitor/job' }
      }
    ]
  },
  {
    path: '/tool/gen-edit',
    component: Layout,
    hidden: true,
    permissions: ['tool:gen:edit'],
    children: [
      {
        path: 'index/:tableId(\\d+)',
        component: () => import('@/views/tool/gen/editTable'),
        name: 'GenEdit',
        meta: { title: 'Modify生成配置', activeMenu: '/tool/gen' }
      }
    ]
  }
]

// 防止连续点击多次Router报错
let routerPush = Router.prototype.push;
Router.prototype.push = function push(location) {
  return routerPush.call(this, location).catch(err => err)
}

export default new Router({
  mode: 'history', // 去掉url中的#
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})
