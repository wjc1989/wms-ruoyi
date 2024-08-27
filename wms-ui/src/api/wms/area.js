import request from '@/utils/request'

// SearchArea列表
export function listWmsArea(query, pageReq) {
    return request({
        url: '/wms/area/list',
        method: 'post',
        data: query,
        params: pageReq
    })
}

// SearchAreaDetail
export function getWmsArea(id) {
    return request({
        url: '/wms/area/' + id,
        method: 'get'
    })
}

// AddArea
export function addWmsArea(data) {
    return request({
        url: '/wms/area',
        method: 'post',
        data: data
    })
}

// ModifyArea
export function updateWmsArea(data) {
    return request({
        url: '/wms/area',
        method: 'put',
        data: data
    })
}

// DeleteArea
export function delWmsArea(id) {
    return request({
        url: '/wms/area/' + id,
        method: 'delete'
    })
}

// ExportArea
export function exportWmsArea(query) {
    return request({
        url: '/wms/area/export',
        method: 'get',
        params: query
    })
}
