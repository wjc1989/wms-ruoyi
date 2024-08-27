import request from '@/utils/request'

// Search Rack列表
export function listWmsRack(query, pageReq) {
  return request({
    url: '/wms/rack/list',
    method: 'post',
    data: query,
    params: pageReq
  })
}

// Search RackDetail
export function getWmsRack(id) {
  return request({
    url: '/wms/rack/' + id,
    method: 'get'
  })
}

// Add Rack
export function addWmsRack(data) {
  return request({
    url: '/wms/rack',
    method: 'post',
    data: data
  })
}

// Modify Rack
export function updateWmsRack(data) {
  return request({
    url: '/wms/rack',
    method: 'put',
    data: data
  })
}

// Delete Rack
export function delWmsRack(id) {
  return request({
    url: '/wms/rack/' + id,
    method: 'delete'
  })
}

// Export Rack
export function exportWmsRack(query) {
  return request({
    url: '/wms/rack/export',
    method: 'get',
    params: query
  })
}
