import request from '@/utils/request'

// SearchCustom flow列表
export function listWmsCustomerTransaction(query, pageReq) {
  return request({
    url: '/wms/customerTransaction/list',
    method: 'post',
    data: query,
    params: pageReq
  })
}

// SearchCustom flowDetail
export function getWmsCustomerTransaction(id) {
  return request({
    url: '/wms/customerTransaction/' + id,
    method: 'get'
  })
}

// AddCustom flow
export function addWmsCustomerTransaction(data) {
  return request({
    url: '/wms/customerTransaction',
    method: 'post',
    data: data
  })
}

// ModifyCustom flow
export function updateWmsCustomerTransaction(data) {
  return request({
    url: '/wms/customerTransaction',
    method: 'put',
    data: data
  })
}

// DeleteCustom flow
export function delWmsCustomerTransaction(id) {
  return request({
    url: '/wms/customerTransaction/' + id,
    method: 'delete'
  })
}

// ExportCustom flow
export function exportWmsCustomerTransaction(query) {
  return request({
    url: '/wms/customerTransaction/export',
    method: 'get',
    params: query
  })
}
