package tqs.backend.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cache_metrics")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CacheMetrics {

    @Id
    private String id = "WEATHER_CACHE";

    @Column(name = "total_requests")
    private Long totalRequests = 0L;

    @Column(name = "cache_hits")
    private Long cacheHits = 0L;

    @Column(name = "cache_misses")
    private Long cacheMisses = 0L;

    public void incrementTotalRequests() {
        this.totalRequests++;
    }

    public void incrementCacheHits() {
        this.cacheHits++;
    }

    public void incrementCacheMisses() {
        this.cacheMisses++;
    }

    public double getHitRatio() {
        if (totalRequests == 0) {
            return 0.0;
        }
        return (double) cacheHits / totalRequests;
    }
}
