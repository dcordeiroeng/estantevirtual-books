package com.estantevirtual.books.utils

import org.slf4j.Logger
import org.springframework.cache.CacheManager
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
@EnableScheduling
class RedisCacheEvict(
    private val cacheManager: CacheManager,
    private val logger: Logger
) {
    @Throws(IllegalStateException::class)
    @Scheduled(fixedRate = 60000)
    fun evictAllCaches() {
        if (cacheManager is RedisCacheManager) {
            val redisCacheManager: RedisCacheManager = cacheManager
            redisCacheManager.cacheNames.forEach { cacheName -> redisCacheManager.getCache(cacheName)?.clear() }
        } else throw IllegalStateException("Cache manager is not RedisCacheManager")
    }
}
