import request from '@/utils/request'

// SearchOperateLogs列表
export function list(query) {
  return request({
    url: '/monitor/operlog/list',
    method: 'get',
    params: query
  })
}

// DeleteOperateLogs
export function delOperlog(operId) {
  return request({
    url: '/monitor/operlog/' + operId,
    method: 'delete'
  })
}

// ClearOperateLogs
export function cleanOperlog() {
  return request({
    url: '/monitor/operlog/clean',
    method: 'delete'
  })
}
