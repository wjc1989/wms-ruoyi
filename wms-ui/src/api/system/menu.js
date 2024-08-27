import request from '@/utils/request'

// SearchMenu 列表
export function listMenu(query) {
  return request({
    url: '/system/menu/list',
    method: 'get',
    params: query
  })
}

// SearchMenu Detail
export function getMenu(menuId) {
  return request({
    url: '/system/menu/' + menuId,
    method: 'get'
  })
}

// SearchMenu 下拉树结构
export function treeselect() {
  return request({
    url: '/system/menu/treeselect',
    method: 'get'
  })
}

// 根据RoleIDSearchMenu 下拉树结构
export function roleMenuTreeselect(roleId) {
  return request({
    url: '/system/menu/roleMenuTreeselect/' + roleId,
    method: 'get'
  })
}

// AddMenu
export function addMenu(data) {
  return request({
    url: '/system/menu',
    method: 'post',
    data: data
  })
}

// ModifyMenu
export function updateMenu(data) {
  return request({
    url: '/system/menu',
    method: 'put',
    data: data
  })
}

// DeleteMenu
export function delMenu(menuId) {
  return request({
    url: '/system/menu/' + menuId,
    method: 'delete'
  })
}
