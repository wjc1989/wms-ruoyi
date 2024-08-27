import request from '@/utils/request'

// SearchOutbound  Detail列表
export function listWmsShipmentOrderDetail(query, pageReq) {
  return request({
    url: '/wms/shipmentOrderDetail/list',
    method: 'post',
    data: query,
    params: pageReq
  })
}

// SearchOutbound  DetailDetail
export function getWmsShipmentOrderDetail(id) {
  return request({
    url: '/wms/shipmentOrderDetail/' + id,
    method: 'get'
  })
}

// AddOutbound  Detail
export function addWmsShipmentOrderDetail(data) {
  return request({
    url: '/wms/shipmentOrderDetail',
    method: 'post',
    data: data
  })
}

// ModifyOutbound  Detail
export function updateWmsShipmentOrderDetail(data) {
  return request({
    url: '/wms/shipmentOrderDetail',
    method: 'put',
    data: data
  })
}

// DeleteOutbound  Detail
export function delWmsShipmentOrderDetail(id) {
  return request({
    url: '/wms/shipmentOrderDetail/' + id,
    method: 'delete'
  })
}

// ExportOutbound  Detail
export function exportWmsShipmentOrderDetail(query) {
  return request({
    url: '/wms/shipmentOrderDetail/export',
    method: 'get',
    params: query
  })
}
