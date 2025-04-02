package tqs.backend.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tqs.backend.Entity.WeatherForecast;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface WeatherForecastRepository extends JpaRepository<WeatherForecast, Long> {
    Optional<WeatherForecast> findByDate(LocalDate date);
    List<WeatherForecast> findByDateBetween(LocalDate startDate, LocalDate endDate);
    List<WeatherForecast> findByExpiresAtAfter(LocalDateTime dateTime);
    void deleteByExpiresAtBefore(LocalDateTime dateTime);
}
