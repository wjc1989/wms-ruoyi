import request from '@/utils/request'

// 获取服务 Info
export function getServer() {
  return request({
    url: '/monitor/server',
    method: 'get'
  })
}
