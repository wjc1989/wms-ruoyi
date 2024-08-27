import request from '@/utils/request'

// Search Carrier列表
export function listWmsCarrier(query, pageReq) {
  return request({
    url: '/wms/carrier/list',
    method: 'post',
    data: query,
    params: pageReq
  })
}

// Search CarrierDetail
export function getWmsCarrier(id) {
  return request({
    url: '/wms/carrier/' + id,
    method: 'get'
  })
}

// Add Carrier
export function addWmsCarrier(data) {
  return request({
    url: '/wms/carrier',
    method: 'post',
    data: data
  })
}

// Modify Carrier
export function updateWmsCarrier(data) {
  return request({
    url: '/wms/carrier',
    method: 'put',
    data: data
  })
}

// Delete Carrier
export function delWmsCarrier(id) {
  return request({
    url: '/wms/carrier/' + id,
    method: 'delete'
  })
}

// Export Carrier
export function exportWmsCarrier(query) {
  return request({
    url: '/wms/carrier/export',
    method: 'get',
    params: query
  })
}
