import request from '@/utils/request'

// SearchCacheDetail
export function getCache() {
  return request({
    url: '/monitor/cache',
    method: 'get'
  })
}

// SearchCacheItem列表
export function listCacheName() {
  return request({
    url: '/monitor/cache/getNames',
    method: 'get'
  })
}

// SearchCache键名列表
export function listCacheKey(cacheName) {
  return request({
    url: '/monitor/cache/getKeys/' + cacheName,
    method: 'get'
  })
}

// SearchCacheContent
export function getCacheValue(cacheName, cacheKey) {
  return request({
    url: '/monitor/cache/getValue/' + cacheName + '/' + cacheKey,
    method: 'get'
  })
}

// 清理指定ItemCache
export function clearCacheName(cacheName) {
  return request({
    url: '/monitor/cache/clearCacheName/' + cacheName,
    method: 'delete'
  })
}

// 清理指定键名Cache
export function clearCacheKey(cacheKey) {
  return request({
    url: '/monitor/cache/clearCacheKey/' + cacheKey,
    method: 'delete'
  })
}

// 清理AllCache
export function clearCacheAll() {
  return request({
    url: '/monitor/cache/clearCacheAll',
    method: 'delete'
  })
}
