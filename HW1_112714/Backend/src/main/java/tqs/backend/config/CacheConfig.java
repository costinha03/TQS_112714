package tqs.backend.config;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.cache.support.SimpleCacheManager;

@Configuration
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();

        // Create a simple ConcurrentMapCache for "weatherCache"
        ConcurrentMapCache weatherCache = new ConcurrentMapCache("weatherCache");

        cacheManager.setCaches(java.util.Collections.singletonList(weatherCache));
        return cacheManager;
    }
}
