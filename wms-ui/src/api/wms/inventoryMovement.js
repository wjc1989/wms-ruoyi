import request from '@/utils/request'

// SearchInventory移动列表
export function listWmsInventoryMovement(query, pageReq) {
  return request({
    url: '/wms/inventoryMovement/list',
    method: 'post',
    data: query,
    params: pageReq
  })
}

// SearchInventory移动Detail
export function getWmsInventoryMovement(id) {
  return request({
    url: '/wms/inventoryMovement/' + id,
    method: 'get'
  })
}

export function addOrUpdateWmsInventoryMovement(data) {
  return request({
    url: '/wms/inventoryMovement/add-or-update',
    method: 'post',
    data: data
  })
}

// AddInventory移动
export function addWmsInventoryMovement(data) {
  return request({
    url: '/wms/inventoryMovement',
    method: 'post',
    data: data
  })
}

// ModifyInventory移动
export function updateWmsInventoryMovement(data) {
  return request({
    url: '/wms/inventoryMovement',
    method: 'put',
    data: data
  })
}

// DeleteInventory移动
export function delWmsInventoryMovement(id) {
  return request({
    url: '/wms/inventoryMovement/' + id,
    method: 'delete'
  })
}

// ExportInventory移动
export function exportWmsInventoryMovement(query) {
  return request({
    url: '/wms/inventoryMovement/export',
    method: 'get',
    params: query
  })
}
