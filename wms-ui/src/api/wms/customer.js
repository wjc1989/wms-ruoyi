import request from '@/utils/request'

// SearchCustom列表
export function listWmsCustomer(query, pageReq) {
  return request({
    url: '/wms/customer/list',
    method: 'post',
    data: query,
    params: pageReq
  })
}

// SearchCustomDetail
export function getWmsCustomer(id) {
  return request({
    url: '/wms/customer/' + id,
    method: 'get'
  })
}

// AddCustom
export function addWmsCustomer(data) {
  return request({
    url: '/wms/customer',
    method: 'post',
    data: data
  })
}

// ModifyCustom
export function updateWmsCustomer(data) {
  return request({
    url: '/wms/customer',
    method: 'put',
    data: data
  })
}

// DeleteCustom
export function delWmsCustomer(id) {
  return request({
    url: '/wms/customer/' + id,
    method: 'delete'
  })
}

// ExportCustom
export function exportWmsCustomer(query) {
  return request({
    url: '/wms/customer/export',
    method: 'get',
    params: query
  })
}
