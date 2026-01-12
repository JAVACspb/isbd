package com.ifmo.isdb.strattanoakmant.configuration;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.Arrays;

@Configuration
public class CacheConfig {

    @Bean
    public CacheManager cacheManager(
            @Value("${app.cache.coursesTtlSeconds:60}") long coursesTtlSeconds,
            @Value("${app.cache.tokenTtlSeconds:3600}") long tokenTtlSeconds
    ) {
        SimpleCacheManager mgr = new SimpleCacheManager();
        mgr.setCaches(Arrays.asList(
                new CaffeineCache("courses",
                        Caffeine.newBuilder().expireAfterWrite(Duration.ofSeconds(coursesTtlSeconds)).build()),
                new CaffeineCache("tokenRole",
                        Caffeine.newBuilder().expireAfterWrite(Duration.ofSeconds(tokenTtlSeconds)).build()),
                new CaffeineCache("tokenUser",
                        Caffeine.newBuilder().expireAfterWrite(Duration.ofSeconds(tokenTtlSeconds)).build())
        ));
        return mgr;
    }
}


