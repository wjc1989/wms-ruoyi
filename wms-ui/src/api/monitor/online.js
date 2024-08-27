import request from '@/utils/request'

// Search在线User 列表
export function list(query) {
  return request({
    url: '/monitor/online/list',
    method: 'get',
    params: query
  })
}

// KickoffUser
export function forceLogout(tokenId) {
  return request({
    url: '/monitor/online/' + tokenId,
    method: 'delete'
  })
}
