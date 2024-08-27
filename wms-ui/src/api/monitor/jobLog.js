import request from '@/utils/request'

// Search调度Logs列表
export function listJobLog(query) {
  return request({
    url: '/monitor/jobLog/list',
    method: 'get',
    params: query
  })
}

// Delete调度Logs
export function delJobLog(jobLogId) {
  return request({
    url: '/monitor/jobLog/' + jobLogId,
    method: 'delete'
  })
}

// Clear调度Logs
export function cleanJobLog() {
  return request({
    url: '/monitor/jobLog/clean',
    method: 'delete'
  })
}
