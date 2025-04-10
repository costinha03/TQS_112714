package tqs.backend.dto;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class CacheStatsDTO {
    private long totalRequests;
    private long hits;
    private long misses;
    private double hitRatio;

    public long getTotalRequests() {
        return totalRequests;
    }

    public void setTotalRequests(long totalRequests) {
        this.totalRequests = totalRequests;
    }

    public long getHits() {
        return hits;
    }

    public void setHits(long hits) {
        this.hits = hits;
    }

    public long getMisses() {
        return misses;
    }

    public void setMisses(long misses) {
        this.misses = misses;
    }

    public double getHitRatio() {
        return hitRatio;
    }

    public void setHitRatio(double hitRatio) {
        this.hitRatio = hitRatio;
    }

    public CacheStatsDTO(long totalRequests, long hits, long misses, double hitRatio) {
        this.totalRequests = totalRequests;
        this.hits = hits;
        this.misses = misses;
        this.hitRatio = hitRatio;
    }

}