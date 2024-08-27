import request from '@/utils/request'

// SearchParams列表
export function listConfig(query) {
  return request({
    url: '/system/config/list',
    method: 'get',
    params: query
  })
}

// SearchParamsDetail
export function getConfig(configId) {
  return request({
    url: '/system/config/' + configId,
    method: 'get'
  })
}

// 根据KeySearchParams值
export function getConfigKey(configKey) {
  return request({
    url: '/system/config/configKey/' + configKey,
    method: 'get'
  })
}

// AddParams配置
export function addConfig(data) {
  return request({
    url: '/system/config',
    method: 'post',
    data: data
  })
}

// ModifyParams配置
export function updateConfig(data) {
  return request({
    url: '/system/config',
    method: 'put',
    data: data
  })
}

// DeleteParams配置
export function delConfig(configId) {
  return request({
    url: '/system/config/' + configId,
    method: 'delete'
  })
}

// RefreshParamsCache
export function refreshCache() {
  return request({
    url: '/system/config/refreshCache',
    method: 'delete'
  })
}
