import request from '@/utils/request'

// SearchInventoryCount据列表
export function listWmsInventoryCheck(query, pageReq) {
  return request({
    url: '/wms/inventoryCheck/list',
    method: 'post',
    data: query,
    params: pageReq
  })
}

// SearchInventoryCount据Detail
export function getWmsInventoryCheck(id) {
  return request({
    url: '/wms/inventoryCheck/' + id,
    method: 'get'
  })
}

// AddInventoryCount据
export function addWmsInventoryCheck(data) {
  return request({
    url: '/wms/inventoryCheck',
    method: 'post',
    data: data
  })
}

export function addOrUpdateWmsInventoryCheck(data) {
  return request({
    url: '/wms/inventoryCheck/add-or-update',
    method: 'post',
    data: data
  })
}

// ModifyInventoryCount据
export function updateWmsInventoryCheck(data) {
  return request({
    url: '/wms/inventoryCheck',
    method: 'put',
    data: data
  })
}

// DeleteInventoryCount据
export function delWmsInventoryCheck(id) {
  return request({
    url: '/wms/inventoryCheck/' + id,
    method: 'delete'
  })
}

// ExportInventoryCount据
export function exportWmsInventoryCheck(query) {
  return request({
    url: '/wms/inventoryCheck/export',
    method: 'get',
    params: query
  })
}
