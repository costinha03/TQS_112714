package tqs.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import tqs.backend.entity.CacheMetrics;

@Service
public class CacheMetricsService {

        private final CacheManager cacheManager;
        private long hits = 0;
        private long misses = 0;
        private static final Logger logger = LoggerFactory.getLogger(CacheMetrics.class);
        public CacheMetricsService(CacheManager cacheManager) {

            this.cacheManager = cacheManager;
        }

        public CacheMetrics getCacheMetrics() {
            // Retrieve the cache from CacheManager
            Cache cache = cacheManager.getCache("weatherCache");
            logger.info("Cache: {}", cache);
            // Track cache hits and misses
            if (cache != null) {
                if (cache.get("weatherData") != null) {
                    hits++;
                } else {
                    misses++;
                }
            }

            // Calculate total requests
            long totalRequests = hits + misses;

            return new CacheMetrics(totalRequests, hits, misses);
        }

        public void resetMetrics() {
            hits = 0;
            misses = 0;
        }

        public long getHits() {
            return hits;
        }

        public long getMisses() {
            return misses;
        }



        public void incrementHits() {
            hits++;
        }

        public void incrementMisses() {
            misses++;
        }
}
