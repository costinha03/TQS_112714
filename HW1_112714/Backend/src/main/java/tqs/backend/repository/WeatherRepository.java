package tqs.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tqs.backend.entity.WeatherForecast;

import java.time.LocalDate;

@Repository
public interface WeatherRepository extends JpaRepository<WeatherForecast, LocalDate> {}
