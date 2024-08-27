import request from '@/utils/request'

// 获取Router
export const getRouters = () => {
  return request({
    url: '/getRouters',
    method: 'get'
  })
}
