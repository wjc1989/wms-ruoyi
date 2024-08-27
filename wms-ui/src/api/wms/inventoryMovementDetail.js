import request from '@/utils/request'

// SearchInventory移动 Detail列表
export function listWmsInventoryMovementDetail(query, pageReq) {
  return request({
    url: '/wms/inventoryMovementDetail/list',
    method: 'post',
    data: query,
    params: pageReq
  })
}

// SearchInventory移动 DetailDetail
export function getWmsInventoryMovementDetail(id) {
  return request({
    url: '/wms/inventoryMovementDetail/' + id,
    method: 'get'
  })
}

// AddInventory移动 Detail
export function addWmsInventoryMovementDetail(data) {
  return request({
    url: '/wms/inventoryMovementDetail',
    method: 'post',
    data: data
  })
}

// ModifyInventory移动 Detail
export function updateWmsInventoryMovementDetail(data) {
  return request({
    url: '/wms/inventoryMovementDetail',
    method: 'put',
    data: data
  })
}

// DeleteInventory移动 Detail
export function delWmsInventoryMovementDetail(id) {
  return request({
    url: '/wms/inventoryMovementDetail/' + id,
    method: 'delete'
  })
}

// ExportInventory移动 Detail
export function exportWmsInventoryMovementDetail(query) {
  return request({
    url: '/wms/inventoryMovementDetail/export',
    method: 'get',
    params: query
  })
}
