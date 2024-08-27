import request from '@/utils/request'

// SearchRole列表
export function listRole(query) {
  return request({
    url: '/system/role/list',
    method: 'get',
    params: query
  })
}

// SearchRoleDetail
export function getRole(roleId) {
  return request({
    url: '/system/role/' + roleId,
    method: 'get'
  })
}

// AddRole
export function addRole(data) {
  return request({
    url: '/system/role',
    method: 'post',
    data: data
  })
}

// ModifyRole
export function updateRole(data) {
  return request({
    url: '/system/role',
    method: 'put',
    data: data
  })
}

// Role
export function dataScope(data) {
  return request({
    url: '/system/role/dataScope',
    method: 'put',
    data: data
  })
}

// RoleStatusModify
export function changeRoleStatus(roleId, status) {
  const data = {
    roleId,
    status
  }
  return request({
    url: '/system/role/changeStatus',
    method: 'put',
    data: data
  })
}

// DeleteRole
export function delRole(roleId) {
  return request({
    url: '/system/role/' + roleId,
    method: 'delete'
  })
}

// SearchRole已AuthorizeUser 列表
export function allocatedUserList(query) {
  return request({
    url: '/system/role/authUser/allocatedList',
    method: 'get',
    params: query
  })
}

// SearchRole未AuthorizeUser 列表
export function unallocatedUserList(query) {
  return request({
    url: '/system/role/authUser/unallocatedList',
    method: 'get',
    params: query
  })
}

// CancelUser AuthorizeRole
export function authUserCancel(data) {
  return request({
    url: '/system/role/authUser/cancel',
    method: 'put',
    data: data
  })
}

// Batch CancelUser AuthorizeRole
export function authUserCancelAll(data) {
  return request({
    url: '/system/role/authUser/cancelAll',
    method: 'put',
    params: data
  })
}

// AuthorizeUser Select
export function authUserSelectAll(data) {
  return request({
    url: '/system/role/authUser/selectAll',
    method: 'put',
    params: data
  })
}
