import request from '@/utils/request'

// SearchSupplier flow列表
export function listWmsSupplierTransaction(query, pageReq) {
  return request({
    url: '/wms/supplierTransaction/list',
    method: 'post',
    data: query,
    params: pageReq
  })
}

// SearchSupplier flowDetail
export function getWmsSupplierTransaction(id) {
  return request({
    url: '/wms/supplierTransaction/' + id,
    method: 'get'
  })
}

// AddSupplier flow
export function addWmsSupplierTransaction(data) {
  return request({
    url: '/wms/supplierTransaction',
    method: 'post',
    data: data
  })
}

// ModifySupplier flow
export function updateWmsSupplierTransaction(data) {
  return request({
    url: '/wms/supplierTransaction',
    method: 'put',
    data: data
  })
}

// DeleteSupplier flow
export function delWmsSupplierTransaction(id) {
  return request({
    url: '/wms/supplierTransaction/' + id,
    method: 'delete'
  })
}

// ExportSupplier flow
export function exportWmsSupplierTransaction(query) {
  return request({
    url: '/wms/supplierTransaction/export',
    method: 'get',
    params: query
  })
}
