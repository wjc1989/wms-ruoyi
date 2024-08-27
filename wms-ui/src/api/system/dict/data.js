import request from '@/utils/request'

// SearchDictData列表
export function listData(query) {
  return request({
    url: '/system/dict/data/list',
    method: 'get',
    params: query
  })
}

// SearchDictDataDetail
export function getData(dictCode) {
  return request({
    url: '/system/dict/data/' + dictCode,
    method: 'get'
  })
}

// 根据DictTypeSearchDictData Info
export function getDicts(dictType) {
  return request({
    url: '/system/dict/data/type/' + dictType,
    method: 'get'
  })
}

// AddDictData
export function addData(data) {
  return request({
    url: '/system/dict/data',
    method: 'post',
    data: data
  })
}

// ModifyDictData
export function updateData(data) {
  return request({
    url: '/system/dict/data',
    method: 'put',
    data: data
  })
}

// DeleteDictData
export function delData(dictCode) {
  return request({
    url: '/system/dict/data/' + dictCode,
    method: 'delete'
  })
}
// DeleteDictData
export function listByTypes(dictTypes) {
  return request({
    url: '/system/dict/data/by_types',
    method: 'POST',
    data: dictTypes
  })
}
