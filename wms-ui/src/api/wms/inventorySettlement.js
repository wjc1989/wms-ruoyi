import request from '@/utils/request'

// SearchInventorySettlement列表
export function listWmsInventorySettlement(query, pageReq) {
  return request({
    url: '/wms/inventorySettlement/list',
    method: 'post',
    data: query,
    params: pageReq
  })
}

// SearchInventorySettlementDetail
export function getWmsInventorySettlement(id) {
  return request({
    url: '/wms/inventorySettlement/' + id,
    method: 'get'
  })
}

// AddInventorySettlement
export function addWmsInventorySettlement(data) {
  return request({
    url: '/wms/inventorySettlement',
    method: 'post',
    data: data
  })
}
export function addOrUpdateWmsInventorySettlement(data) {
  return request({
    url: '/wms/inventorySettlement/add-or-update',
    method: 'post',
    data: data
  })
}
// ModifyInventorySettlement
export function updateWmsInventorySettlement(data) {
  return request({
    url: '/wms/inventorySettlement',
    method: 'put',
    data: data
  })
}

// DeleteInventorySettlement
export function delWmsInventorySettlement(id) {
  return request({
    url: '/wms/inventorySettlement/' + id,
    method: 'delete'
  })
}

// ExportInventorySettlement
export function exportWmsInventorySettlement(query) {
  return request({
    url: '/wms/inventorySettlement/export',
    method: 'get',
    params: query
  })
}
