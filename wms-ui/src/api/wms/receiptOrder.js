import request from '@/utils/request'

// SearchInbound列表
export function listWmsReceiptOrder(query, pageReq) {
  return request({
    url: '/wms/receiptOrder/list',
    method: 'post',
    data: query,
    params: pageReq
  })
}

// SearchInboundDetail
export function getWmsReceiptOrder(id) {
  return request({
    url: '/wms/receiptOrder/' + id,
    method: 'get'
  })
}

// AddInbound
export function addWmsReceiptOrder(data) {
  return request({
    url: '/wms/receiptOrder',
    method: 'post',
    data: data
  })
}
// Add Or编辑Inbound
export function addOrUpdateWmsReceiptOrder(data) {
  return request({
    url: '/wms/receiptOrder/add-or-update',
    method: 'post',
    data: data
  })
}

// ModifyInbound
export function updateWmsReceiptOrder(data) {
  return request({
    url: '/wms/receiptOrder',
    method: 'put',
    data: data
  })
}

// DeleteInbound
export function delWmsReceiptOrder(id) {
  return request({
    url: '/wms/receiptOrder/' + id,
    method: 'delete'
  })
}

// ExportInbound
export function exportWmsReceiptOrder(query) {
  return request({
    url: '/wms/receiptOrder/export',
    method: 'get',
    params: query
  })
}
