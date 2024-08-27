import request from '@/utils/request'

// Search生成表Data
export function listTable(query) {
  return request({
    url: '/tool/gen/list',
    method: 'get',
    params: query
  })
}
// SearchdbData库列表
export function listDbTable(query) {
  return request({
    url: '/tool/gen/db/list',
    method: 'get',
    params: query
  })
}

// Search表Detail Info
export function getGenTable(tableId) {
  return request({
    url: '/tool/gen/' + tableId,
    method: 'get'
  })
}

// Modify代码生成 Info
export function updateGenTable(data) {
  return request({
    url: '/tool/gen',
    method: 'put',
    data: data
  })
}

// Import表
export function importTable(data) {
  return request({
    url: '/tool/gen/importTable',
    method: 'post',
    params: data
  })
}

// 预览生成代码
export function previewTable(tableId) {
  return request({
    url: '/tool/gen/preview/' + tableId,
    method: 'get'
  })
}

// Delete表Data
export function delTable(tableId) {
  return request({
    url: '/tool/gen/' + tableId,
    method: 'delete'
  })
}

// 生成代码（Custom 义路径）
export function genCode(tableName) {
  return request({
    url: '/tool/gen/genCode/' + tableName,
    method: 'get'
  })
}

// 同步Data库
export function synchDb(tableName) {
  return request({
    url: '/tool/gen/synchDb/' + tableName,
    method: 'get'
  })
}
