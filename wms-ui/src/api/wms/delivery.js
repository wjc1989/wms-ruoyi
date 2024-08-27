import request from '@/utils/request'

// SearchOutbound Record列表
export function listWmsDelivery(query, pageReq) {
  return request({
    url: '/wms/delivery/list',
    method: 'post',
    data: query,
    params: pageReq
  })
}

// SearchOutbound RecordDetail
export function getWmsDelivery(id) {
  return request({
    url: '/wms/delivery/' + id,
    method: 'get'
  })
}

// AddOutbound Record
export function addWmsDelivery(data) {
  return request({
    url: '/wms/delivery',
    method: 'post',
    data: data
  })
}

// ModifyOutbound Record
export function updateWmsDelivery(data) {
  return request({
    url: '/wms/delivery',
    method: 'put',
    data: data
  })
}

// DeleteOutbound Record
export function delWmsDelivery(id) {
  return request({
    url: '/wms/delivery/' + id,
    method: 'delete'
  })
}

// ExportOutbound Record
export function exportWmsDelivery(query) {
  return request({
    url: '/wms/delivery/export',
    method: 'get',
    params: query
  })
}
