import request from '@/utils/request'

// SearchInventory Record列表
export function listWmsInventoryHistory(query, pageReq) {
  return request({
    url: '/wms/inventoryHistory/list',
    method: 'post',
    data: query,
    params: pageReq
  })
}

// SearchInventory RecordDetail
export function getWmsInventoryHistory(id) {
  return request({
    url: '/wms/inventoryHistory/' + id,
    method: 'get'
  })
}

// AddInventory Record
export function addWmsInventoryHistory(data) {
  return request({
    url: '/wms/inventoryHistory',
    method: 'post',
    data: data
  })
}

// ModifyInventory Record
export function updateWmsInventoryHistory(data) {
  return request({
    url: '/wms/inventoryHistory',
    method: 'put',
    data: data
  })
}

// DeleteInventory Record
export function delWmsInventoryHistory(id) {
  return request({
    url: '/wms/inventoryHistory/' + id,
    method: 'delete'
  })
}

// ExportInventory Record
export function exportWmsInventoryHistory(query) {
  return request({
    url: '/wms/inventoryHistory/export',
    method: 'get',
    params: query
  })
}
