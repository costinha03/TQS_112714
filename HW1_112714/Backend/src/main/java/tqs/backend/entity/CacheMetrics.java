package tqs.backend.entity;

public class CacheMetrics {
    private long totalRequests;
    private long hits;
    private long misses;

    // Constructor
    public CacheMetrics(long totalRequests, long hits, long misses) {
        this.totalRequests = totalRequests;
        this.hits = hits;
        this.misses = misses;
    }

    // Getters and Setters
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
        return misses;}
    public void setMisses(long misses) {
        this.misses = misses;
    }

}
