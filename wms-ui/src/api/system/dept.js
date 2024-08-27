import request from '@/utils/request'

// SearchDepartment列表
export function listDept(query) {
  return request({
    url: '/system/dept/list',
    method: 'get',
    params: query
  })
}

// SearchDepartment列表（排除节点）
export function listDeptExcludeChild(deptId) {
  return request({
    url: '/system/dept/list/exclude/' + deptId,
    method: 'get'
  })
}

// SearchDepartmentDetail
export function getDept(deptId) {
  return request({
    url: '/system/dept/' + deptId,
    method: 'get'
  })
}

// SearchDepartment下拉树结构
export function treeselect() {
  return request({
    url: '/system/dept/treeselect',
    method: 'get'
  })
}

// 根据RoleIDSearchDepartment树结构
export function roleDeptTreeselect(roleId) {
  return request({
    url: '/system/dept/roleDeptTreeselect/' + roleId,
    method: 'get'
  })
}

// AddDepartment
export function addDept(data) {
  return request({
    url: '/system/dept',
    method: 'post',
    data: data
  })
}

// ModifyDepartment
export function updateDept(data) {
  return request({
    url: '/system/dept',
    method: 'put',
    data: data
  })
}

// DeleteDepartment
export function delDept(deptId) {
  return request({
    url: '/system/dept/' + deptId,
    method: 'delete'
  })
}
