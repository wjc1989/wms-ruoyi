import request from '@/utils/request'

// SearchNotice列表
export function listNotice(query) {
  return request({
    url: '/system/notice/list',
    method: 'get',
    params: query
  })
}

// SearchNoticeDetail
export function getNotice(noticeId) {
  return request({
    url: '/system/notice/' + noticeId,
    method: 'get'
  })
}

// AddNotice
export function addNotice(data) {
  return request({
    url: '/system/notice',
    method: 'post',
    data: data
  })
}

// ModifyNotice
export function updateNotice(data) {
  return request({
    url: '/system/notice',
    method: 'put',
    data: data
  })
}

// DeleteNotice
export function delNotice(noticeId) {
  return request({
    url: '/system/notice/' + noticeId,
    method: 'delete'
  })
}
