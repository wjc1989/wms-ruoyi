import request from '@/utils/request'

// SearchPost列表
export function listPost(query) {
  return request({
    url: '/system/post/list',
    method: 'get',
    params: query
  })
}

// SearchPostDetail
export function getPost(postId) {
  return request({
    url: '/system/post/' + postId,
    method: 'get'
  })
}

// AddPost
export function addPost(data) {
  return request({
    url: '/system/post',
    method: 'post',
    data: data
  })
}

// ModifyPost
export function updatePost(data) {
  return request({
    url: '/system/post',
    method: 'put',
    data: data
  })
}

// DeletePost
export function delPost(postId) {
  return request({
    url: '/system/post/' + postId,
    method: 'delete'
  })
}
