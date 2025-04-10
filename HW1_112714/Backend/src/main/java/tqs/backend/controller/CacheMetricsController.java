package tqs.backend.controller;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tqs.backend.entity.CacheMetrics;
import tqs.backend.service.CacheMetricsService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping
public class CacheMetricsController {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(CacheMetricsController.class);
    private final CacheMetricsService cacheMetricsService;

    public CacheMetricsController(CacheMetricsService cacheMetricsService) {
        this.cacheMetricsService = cacheMetricsService;
    }

    @GetMapping("/cache/metrics")
    @CrossOrigin(origins = "http://localhost:3000")
    public CacheMetrics getCacheMetrics() {
        logger.info("Fetching cache metrics");
        return cacheMetricsService.getCacheMetrics();

    }
}
