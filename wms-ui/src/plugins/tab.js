import store from '@/store'
import router from '@/router';

export default {
  // Refresh当前tab页签
  refreshPage(obj) {
    const { path, query, matched } = router.currentRoute;
    if (obj === undefined) {
      matched.forEach((m) => {
        if (m.components && m.components.default && m.components.default.name) {
          if (!['Layout', 'ParentView'].includes(m.components.default.name)) {
            obj = { name: m.components.default.name, path: path, query: query };
          }
        }
      });
    }
    return store.dispatch('tagsView/delCachedView', obj).then(() => {
      const { path, query } = obj
      router.replace({
        path: '/redirect' + path,
        query: query
      })
    })
  },
  // Close Currenttab页签，打开新页签
  closeOpenPage(obj) {
    console.log("close Open Page,",router.currentRoute)
    store.dispatch("tagsView/delView", router.currentRoute);
    if (obj !== undefined) {
      return router.push(obj);
    }
  },
  // Close指定tab页签
  closePage(obj) {
    if (obj === undefined) {
      return store.dispatch('tagsView/delView', router.currentRoute).then(({ lastPath }) => {
        return router.push(lastPath || '/');
      });
    }
    return store.dispatch('tagsView/delView', obj);
  },
  // Close所有tab页签
  closeAllPage() {
    return store.dispatch('tagsView/delAllViews');
  },
  // Close左侧tab页签
  closeLeftPage(obj) {
    return store.dispatch('tagsView/delLeftTags', obj || router.currentRoute);
  },
  // Close右侧tab页签
  closeRightPage(obj) {
    return store.dispatch('tagsView/delRightTags', obj || router.currentRoute);
  },
  // Close其他tab页签
  closeOtherPage(obj) {
    return store.dispatch('tagsView/delOthersViews', obj || router.currentRoute);
  },
  // Addtab页签
  openPage(title, url, params) {
    var obj = { path: url, meta: { title: title } }
    store.dispatch('tagsView/addView', obj);
    return router.push({ path: url, query: params });
  },
  // Modifytab页签
  updatePage(obj) {
    return store.dispatch('tagsView/updateVisitedView', obj);
  }
}
