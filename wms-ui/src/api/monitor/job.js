import request from '@/utils/request'

// Search定时 Task调度列表
export function listJob(query) {
  return request({
    url: '/monitor/job/list',
    method: 'get',
    params: query
  })
}

// Search定时 Task调度Detail
export function getJob(jobId) {
  return request({
    url: '/monitor/job/' + jobId,
    method: 'get'
  })
}

// Add定时 Task调度
export function addJob(data) {
  return request({
    url: '/monitor/job',
    method: 'post',
    data: data
  })
}

// Modify定时 Task调度
export function updateJob(data) {
  return request({
    url: '/monitor/job',
    method: 'put',
    data: data
  })
}

// Delete定时 Task调度
export function delJob(jobId) {
  return request({
    url: '/monitor/job/' + jobId,
    method: 'delete'
  })
}

// StatusModify
export function changeJobStatus(jobId, status) {
  const data = {
    jobId,
    status
  }
  return request({
    url: '/monitor/job/changeStatus',
    method: 'put',
    data: data
  })
}


// 定时 Task立即执行一次
export function runJob(jobId, jobGroup) {
  const data = {
    jobId,
    jobGroup
  }
  return request({
    url: '/monitor/job/run',
    method: 'put',
    data: data
  })
}
