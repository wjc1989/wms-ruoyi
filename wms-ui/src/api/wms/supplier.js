import request from '@/utils/request'

// SearchSupplier列表
export function listWmsSupplier(query, pageReq) {
  return request({
    url: '/wms/supplier/list',
    method: 'post',
    data: query,
    params: pageReq
  })
}

// SearchSupplierDetail
export function getWmsSupplier(id) {
  return request({
    url: '/wms/supplier/' + id,
    method: 'get'
  })
}

// AddSupplier
export function addWmsSupplier(data) {
  return request({
    url: '/wms/supplier',
    method: 'post',
    data: data
  })
}

// ModifySupplier
export function updateWmsSupplier(data) {
  return request({
    url: '/wms/supplier',
    method: 'put',
    data: data
  })
}

// DeleteSupplier
export function delWmsSupplier(id) {
  return request({
    url: '/wms/supplier/' + id,
    method: 'delete'
  })
}

// ExportSupplier
export function exportWmsSupplier(query) {
  return request({
    url: '/wms/supplier/export',
    method: 'get',
    params: query
  })
}
