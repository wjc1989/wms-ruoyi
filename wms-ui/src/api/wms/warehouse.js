import request from '@/utils/request'

// SearchWarehouse列表
export function listWmsWarehouse(query, pageReq) {
  return request({
    url: '/wms/warehouse/list',
    method: 'post',
    data: query,
    params: pageReq
  })
}

// SearchWarehouseDetail
export function getWmsWarehouse(id) {
  return request({
    url: '/wms/warehouse/' + id,
    method: 'get'
  })
}

// AddWarehouse
export function addWmsWarehouse(data) {
  return request({
    url: '/wms/warehouse',
    method: 'post',
    data: data
  })
}

// ModifyWarehouse
export function updateWmsWarehouse(data) {
  return request({
    url: '/wms/warehouse',
    method: 'put',
    data: data
  })
}

// DeleteWarehouse
export function delWmsWarehouse(id) {
  return request({
    url: '/wms/warehouse/' + id,
    method: 'delete'
  })
}

// ExportWarehouse
export function exportWmsWarehouse(query) {
  return request({
    url: '/wms/warehouse/export',
    method: 'get',
    params: query
  })
}
