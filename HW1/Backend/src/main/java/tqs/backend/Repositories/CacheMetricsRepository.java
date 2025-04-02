package tqs.backend.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tqs.backend.Entity.CacheMetrics;

@Repository
public interface CacheMetricsRepository extends JpaRepository<CacheMetrics, Long> {

}
