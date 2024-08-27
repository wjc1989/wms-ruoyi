import request from '@/utils/request'

// SearchInbound Detail列表
export function listWmsReceiptOrderDetail(query, pageReq) {
  return request({
    url: '/wms/receiptOrderDetail/list',
    method: 'post',
    data: query,
    params: pageReq
  })
}

// SearchInbound DetailDetail
export function getWmsReceiptOrderDetail(id) {
  return request({
    url: '/wms/receiptOrderDetail/' + id,
    method: 'get'
  })
}

// AddInbound Detail
export function addWmsReceiptOrderDetail(data) {
  return request({
    url: '/wms/receiptOrderDetail',
    method: 'post',
    data: data
  })
}

// ModifyInbound Detail
export function updateWmsReceiptOrderDetail(data) {
  return request({
    url: '/wms/receiptOrderDetail',
    method: 'put',
    data: data
  })
}

// DeleteInbound Detail
export function delWmsReceiptOrderDetail(id) {
  return request({
    url: '/wms/receiptOrderDetail/' + id,
    method: 'delete'
  })
}

// ExportInbound Detail
export function exportWmsReceiptOrderDetail(query) {
  return request({
    url: '/wms/receiptOrderDetail/export',
    method: 'get',
    params: query
  })
}
