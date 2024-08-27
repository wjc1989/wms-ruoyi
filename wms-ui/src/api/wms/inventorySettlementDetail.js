import request from '@/utils/request'

// SearchInventorySettlement明细列表
export function listWmsInventorySettlementDetail(query, pageReq) {
  return request({
    url: '/wms/inventorySettlementDetail/list',
    method: 'post',
    data: query,
    params: pageReq
  })
}

// SearchInventory Info
export function listByTime(query) {
  return request({
    url: '/wms/inventorySettlementDetail/listByTime',
    method: 'post',
    data: query,
  })
}

// SearchInventorySettlement明细Detail
export function getWmsInventorySettlementDetail(id) {
  return request({
    url: '/wms/inventorySettlementDetail/' + id,
    method: 'get'
  })
}

// AddInventorySettlement明细
export function addWmsInventorySettlementDetail(data) {
  return request({
    url: '/wms/inventorySettlementDetail',
    method: 'post',
    data: data
  })
}

// ModifyInventorySettlement明细
export function updateWmsInventorySettlementDetail(data) {
  return request({
    url: '/wms/inventorySettlementDetail',
    method: 'put',
    data: data
  })
}

// DeleteInventorySettlement明细
export function delWmsInventorySettlementDetail(id) {
  return request({
    url: '/wms/inventorySettlementDetail/' + id,
    method: 'delete'
  })
}

// ExportInventorySettlement明细
export function exportWmsInventorySettlementDetail(query) {
  return request({
    url: '/wms/inventorySettlementDetail/export',
    method: 'get',
    params: query
  })
}
