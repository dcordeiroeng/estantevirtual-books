package com.estantevirtual.utils

import org.springframework.cache.CacheManager
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.stereotype.Component

@Component
class RedisCacheEvict(
    private val cacheManager: CacheManager
) {
    fun evictAllCaches() {
        if (cacheManager is RedisCacheManager) {
            val redisCacheManager: RedisCacheManager = cacheManager
            redisCacheManager.cacheNames.forEach { cacheName -> redisCacheManager.getCache(cacheName)?.clear() }
            println("All caches were evicted")
        } else {
            throw IllegalStateException("Cache manager is not RedisCacheManager")
        }
    }
}
