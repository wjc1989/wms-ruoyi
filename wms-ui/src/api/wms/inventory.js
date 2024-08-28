import request from '@/utils/request'

// SearchInventory列表
export function listWmsInventory(query, pageReq) {
  return request({
    url: '/wms/inventory/list',
    method: 'post',
    data: query,
    params: pageReq
  })
}

// SearchWarnInventory列表
export function listWarnInventory(pageReq) {
  return request({
    url: '/wms/inventory/warnList',
    method: 'post',
    params: pageReq
  })
}

// SearchInventoryDetail
export function getWmsInventory(id) {
  return request({
    url: '/wms/inventory/' + id,
    method: 'get'
  })
}

// AddInventory
export function addWmsInventory(data) {
  return request({
    url: '/wms/inventory',
    method: 'post',
    data: data
  })
}

// ModifyInventory
export function updateWmsInventory(data) {
  return request({
    url: '/wms/inventory',
    method: 'put',
    data: data
  })
}

// DeleteInventory
export function delWmsInventory(id) {
  return request({
    url: '/wms/inventory/' + id,
    method: 'delete'
  })
}
// DeleteInventory
export function delWmsInventoryByItem(id) {
  return request({
    url: '/wms/inventory/item/' + id,
    method: 'delete'
  })
}

// DeleteInventory
export function delWmsInventoryByWarehouse(id) {
  return request({
    url: '/wms/inventory/warehouse/' + id,
    method: 'delete'
  })
}

// ExportInventory
export function exportWmsInventory(query) {
  return request({
    url: '/wms/inventory/export',
    method: 'get',
    params: query
  })
}

export function getGoodsCount(ids){
  return request({
    url:'/wms/inventory/getGoodsCount/'+ids,
    method:'post',
    headers:{
      repeatSubmit:false
    }
  })
}
