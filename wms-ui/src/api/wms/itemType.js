import request from '@/utils/request'

// SearchItemType表列表
export function listWmsItemType(query, pageReq) {
  return request({
    url: '/wms/itemType/list',
    method: 'post',
    data: query,
    params: pageReq
  })
}
// SearchDepartment下拉树结构
export function itemTypeTreeselect() {
  return request({
    url: '/wms/itemType/treeselect',
    method: 'get'
  })
}
// SearchItemType表列表
export function listWmsItemTypeNoPage(query, pageReq) {
  return request({
    url: '/wms/itemType/noPageList',
    method: 'get',
    data: query,
    params: pageReq
  })
}

// SearchItemType表Detail
export function getWmsItemType(itemTypeId) {
  return request({
    url: '/wms/itemType/' + itemTypeId,
    method: 'get'
  })
}

// AddItemType表
export function addWmsItemType(data) {
  return request({
    url: '/wms/itemType',
    method: 'post',
    data: data
  })
}

// ModifyItemType表
export function updateWmsItemType(data) {
  return request({
    url: '/wms/itemType',
    method: 'put',
    data: data
  })
}

// DeleteItemType表
export function delWmsItemType(itemTypeId) {
  return request({
    url: '/wms/itemType/' + itemTypeId,
    method: 'delete'
  })
}

// ExportItemType表
export function exportWmsItemType(query) {
  return request({
    url: '/wms/itemType/export',
    method: 'get',
    params: query
  })
}
