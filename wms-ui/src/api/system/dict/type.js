import request from '@/utils/request'

// SearchDictType列表
export function listType(query) {
  return request({
    url: '/system/dict/type/list',
    method: 'get',
    params: query
  })
}

// SearchDictTypeDetail
export function getType(dictId) {
  return request({
    url: '/system/dict/type/' + dictId,
    method: 'get'
  })
}

// AddDictType
export function addType(data) {
  return request({
    url: '/system/dict/type',
    method: 'post',
    data: data
  })
}

// ModifyDictType
export function updateType(data) {
  return request({
    url: '/system/dict/type',
    method: 'put',
    data: data
  })
}

// DeleteDictType
export function delType(dictId) {
  return request({
    url: '/system/dict/type/' + dictId,
    method: 'delete'
  })
}

// RefreshDictCache
export function refreshCache() {
  return request({
    url: '/system/dict/type/refreshCache',
    method: 'delete'
  })
}

// 获取DictSelect 框列表
export function optionselect() {
  return request({
    url: '/system/dict/type/optionselect',
    method: 'get'
  })
}
