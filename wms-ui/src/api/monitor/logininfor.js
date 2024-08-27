import request from '@/utils/request'

// SearchLoginLogs列表
export function list(query) {
  return request({
    url: '/monitor/logininfor/list',
    method: 'get',
    params: query
  })
}

// DeleteLoginLogs
export function delLogininfor(infoId) {
  return request({
    url: '/monitor/logininfor/' + infoId,
    method: 'delete'
  })
}

// ClearLoginLogs
export function cleanLogininfor() {
  return request({
    url: '/monitor/logininfor/clean',
    method: 'delete'
  })
}
