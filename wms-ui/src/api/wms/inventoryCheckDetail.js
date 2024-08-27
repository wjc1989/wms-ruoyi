import request from '@/utils/request'

// SearchInventoryCount据 Detail列表
export function listWmsInventoryCheckDetail(query, pageReq) {
  return request({
    url: '/wms/inventoryCheckDetail/list',
    method: 'post',
    data: query,
    params: pageReq
  })
}

// SearchInventoryCount据 DetailDetail
export function getWmsInventoryCheckDetail(id) {
  return request({
    url: '/wms/inventoryCheckDetail/' + id,
    method: 'get'
  })
}

// AddInventoryCount据 Detail
export function addWmsInventoryCheckDetail(data) {
  return request({
    url: '/wms/inventoryCheckDetail',
    method: 'post',
    data: data
  })
}

// ModifyInventoryCount据 Detail
export function updateWmsInventoryCheckDetail(data) {
  return request({
    url: '/wms/inventoryCheckDetail',
    method: 'put',
    data: data
  })
}

// DeleteInventoryCount据 Detail
export function delWmsInventoryCheckDetail(id) {
  return request({
    url: '/wms/inventoryCheckDetail/' + id,
    method: 'delete'
  })
}

// ExportInventoryCount据 Detail
export function exportWmsInventoryCheckDetail(query) {
  return request({
    url: '/wms/inventoryCheckDetail/export',
    method: 'get',
    params: query
  })
}
