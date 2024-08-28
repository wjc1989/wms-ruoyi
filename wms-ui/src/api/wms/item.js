import request from '@/utils/request'

// SearchItem列表
export function listWmsItem(query, pageReq) {
  return request({
    url: '/wms/item/list',
    method: 'post',
    data: query,
    params: pageReq
  })
}
//listWithCount
export function listWmsItemWithCount(query, pageReq) {
  return request({
    url: '/wms/item/listCount',
    method: 'post',
    data: query,
    params: pageReq
  })
}
// SearchExpiryItem列表
export function listExpiryItem(pageReq) {
  return request({
    url: '/wms/item/expiryList',
    method: 'post',
    params: pageReq
  })
}
// SearchItem列表
export function allWmsItem(query) {
  return request({
    url: '/wms/item/all',
    method: 'post',
    data: query
  })
}

// SearchItemDetail
export function getWmsItem(id) {
  return request({
    url: '/wms/item/' + id,
    method: 'get'
  })
}

// AddItem
export function addWmsItem(data) {
  return request({
    url: '/wms/item',
    method: 'post',
    data: data
  })
}

// ModifyItem
export function updateWmsItem(data) {
  return request({
    url: '/wms/item',
    method: 'put',
    data: data
  })
}

// DeleteItem
export function delWmsItem(id) {
  return request({
    url: '/wms/item/' + id,
    method: 'delete'
  })
}

// ExportItem
export function exportWmsItem(query) {
  return request({
    url: '/wms/item/export',
    method: 'get',
    params: query
  })
}


