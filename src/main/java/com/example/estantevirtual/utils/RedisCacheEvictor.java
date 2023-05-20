package com.example.estantevirtual.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.stereotype.Component;

@Component
public class RedisCacheEvictor {

    private final CacheManager cacheManager;

    @Autowired
    public RedisCacheEvictor(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public void evictAllCaches() {
        if (cacheManager instanceof RedisCacheManager) {
            RedisCacheManager redisCacheManager = (RedisCacheManager) cacheManager;
            redisCacheManager.getCacheNames().forEach(cacheName -> redisCacheManager.getCache(cacheName).clear());
            System.out.println("All caches were evicted");
        } else {
            throw new IllegalStateException("Cache manager is not RedisCacheManager");
        }
    }
}
