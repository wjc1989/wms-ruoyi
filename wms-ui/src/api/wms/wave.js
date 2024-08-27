import request from '@/utils/request'

// SearchWave 列表
export function listWave(query, pageReq) {
  return request({
    url: '/wms/wave/list',
    method: 'post',
    data: query,
    params: pageReq
  })
}

// SearchWave Detail
export function getWave(id) {
  return request({
    url: '/wms/wave/shipment/' + id,
    method: 'get'
  })
}

// AddWave
export function addWave(data) {
  return request({
    url: '/wms/wave/shipment/add',
    method: 'post',
    data: data
  })
}

// ModifyWave
export function updateWave(data) {
  return request({
    url: '/wms/wave',
    method: 'put',
    data: data
  })
}

// DeleteWave
export function delWave(id) {
  return request({
    url: '/wms/wave/shipment/' + id,
    method: 'delete'
  })
}

// ExportWave
export function exportWave(query) {
  return request({
    url: '/wms/wave/export',
    method: 'get',
    params: query
  })
}

// Wave  Distribute Warehouse
export function waveAllocatedInventory(params) {
  return request({
    url: '/wms/wave/shipment/allocated/',
    method: 'post',
    params
  })
}
// 应用Wave 作业的Warehouse
export function confirmWave(data) {
  return request({
    url: '/wms/wave/shipment/confirmWave' ,
    method: 'post',
    data: data
  })
}
// CancelWave Task
export function cancelAllocatedInventory(id) {
  return request({
    url: '/wms/wave/shipment/cancelAllocatedInventory/'+ id ,
    method: 'post'
  })
}


export function getReceiptWave(id) {
  return request({
    url: '/wms/wave/receipt/' + id,
    method: 'get'
  })
}

// AddWave
export function addWaveForReceipt(data) {
  return request({
    url: '/wms/wave/receipt/add',
    method: 'post',
    data: data
  })
}

// DeleteWave
export function delWaveForReceipt(id) {
  return request({
    url: '/wms/wave/receipt/' + id,
    method: 'delete'
  })
}

// Wave  Distribute Warehouse
export function waveAllocatedInventoryForReceipt(params) {
  return request({
    url: '/wms/wave/receipt/allocated/',
    method: 'post',
    params
  })
}
// 应用Wave 作业的Warehouse
export function confirmWaveForReceipt(data) {
  return request({
    url: '/wms/wave/receipt/confirmWave' ,
    method: 'post',
    data: data
  })
}
// CancelWave Task
export function cancelAllocatedInventoryForReceipt(id) {
  return request({
    url: '/wms/wave/receipt/cancelAllocatedInventory/'+ id ,
    method: 'post'
  })
}
