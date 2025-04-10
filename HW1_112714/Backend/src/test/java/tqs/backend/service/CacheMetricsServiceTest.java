package tqs.backend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import tqs.backend.entity.CacheMetrics;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CacheMetricsServiceTest {

    @Mock
    private CacheManager cacheManager;

    @Mock
    private Cache cache;

    @InjectMocks
    private CacheMetricsService cacheMetricsService;

    private static final String CACHE_NAME = "weatherCache";

 /*   @BeforeEach
    void setUp() {
        when(cacheManager.getCache(CACHE_NAME)).thenReturn(cache);
        cacheMetricsService.resetMetrics();
    }

    @Test
    void whenNewService_thenMetricsAreZero() {
        CacheMetrics metrics = cacheMetricsService.getCacheMetrics();
        assertThat(metrics.getTotalRequests()).isZero();
        assertThat(metrics.getHits()).isZero();
        assertThat(metrics.getMisses()).isZero();
    }

    @Test
    void whenIncrementHits_thenHitsAndTotalRequestsIncrease() {
        cacheMetricsService.incrementHits();
        CacheMetrics metrics = cacheMetricsService.getCacheMetrics();

        assertThat(metrics.getHits()).isEqualTo(1);
        assertThat(metrics.getTotalRequests()).isEqualTo(1);
        assertThat(metrics.getMisses()).isZero();
    }

    @Test
    void whenIncrementMisses_thenMissesAndTotalRequestsIncrease() {
        cacheMetricsService.incrementMisses();
        CacheMetrics metrics = cacheMetricsService.getCacheMetrics();

        assertThat(metrics.getMisses()).isEqualTo(1);
        assertThat(metrics.getTotalRequests()).isEqualTo(1);
        assertThat(metrics.getHits()).isZero();
    }

    @Test
    void whenMultipleIncrements_thenMetricsAccumulate() {
        cacheMetricsService.incrementHits();
        cacheMetricsService.incrementHits();
        cacheMetricsService.incrementHits();
        cacheMetricsService.incrementMisses();
        cacheMetricsService.incrementMisses();

        CacheMetrics metrics = cacheMetricsService.getCacheMetrics();
        assertThat(metrics.getHits()).isEqualTo(3);
        assertThat(metrics.getMisses()).isEqualTo(2);
        assertThat(metrics.getTotalRequests()).isEqualTo(5);
    }

    @Test
    void whenResetMetrics_thenAllMetricsAreZero() {
        cacheMetricsService.incrementHits();
        cacheMetricsService.incrementMisses();

        cacheMetricsService.resetMetrics();
        CacheMetrics metrics = cacheMetricsService.getCacheMetrics();

        assertThat(metrics.getHits()).isZero();
        assertThat(metrics.getMisses()).isZero();
        assertThat(metrics.getTotalRequests()).isZero();
    }

    @Test
    void whenGetCacheMetrics_thenReturnCurrentMetrics() {
        // Add hits and misses
        for (int i = 0; i < 3; i++) {
            cacheMetricsService.incrementHits();
        }
        for (int i = 0; i < 2; i++) {
            cacheMetricsService.incrementMisses();
        }

        CacheMetrics metrics = cacheMetricsService.getCacheMetrics();
        assertThat(metrics.getHits()).isEqualTo(3);
        assertThat(metrics.getMisses()).isEqualTo(2);
        assertThat(metrics.getTotalRequests()).isEqualTo(5);
    }*/
}