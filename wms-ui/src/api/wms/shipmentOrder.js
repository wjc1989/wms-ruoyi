import request from '@/utils/request'

// SearchOutbound 列表
export function listWmsShipmentOrder(query, pageReq) {
  return request({
    url: '/wms/shipmentOrder/list',
    method: 'post',
    data: query,
    params: pageReq
  })
}

// SearchOutbound Detail
export function getWmsShipmentOrder(id) {
  return request({
    url: '/wms/shipmentOrder/' + id,
    method: 'get'
  })
}

export function addOrUpdateWmsShipmentOrder(data) {
  return request({
    url: '/wms/shipmentOrder/add-or-update',
    method: 'post',
    data: data
  })
}

// AddOutbound
export function addWmsShipmentOrder(data) {
  return request({
    url: '/wms/shipmentOrder',
    method: 'post',
    data: data
  })
}

// ModifyOutbound
export function updateWmsShipmentOrder(data) {
  return request({
    url: '/wms/shipmentOrder',
    method: 'put',
    data: data
  })
}

// DeleteOutbound
export function delWmsShipmentOrder(id) {
  return request({
    url: '/wms/shipmentOrder/' + id,
    method: 'delete'
  })
}

// ExportOutbound
export function exportWmsShipmentOrder(query) {
  return request({
    url: '/wms/shipmentOrder/export',
    method: 'get',
    params: query
  })
}

// 个订 Distribute Warehouse
export function allocatedInventory(params) {
  return request({
    url: '/wms/shipmentOrder/allocated',
    params,
    method: 'post'
  })
}
