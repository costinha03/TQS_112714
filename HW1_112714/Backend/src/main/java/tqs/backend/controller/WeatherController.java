package tqs.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tqs.backend.dto.CacheStatsDTO;
import tqs.backend.entity.WeatherForecast;
import tqs.backend.service.WeatherService;

import java.util.List;

@RestController
@RequestMapping("/api/weather")
@CrossOrigin(origins = "http://localhost:3000")
public class WeatherController {
    private final WeatherService weatherService;


    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    // Get the weekly weather forecast from the database
    @GetMapping
    public List<WeatherForecast> getWeather() {
        return weatherService.getWeeklyWeather();
    }

    // Get today's weather forecast from the database
    @GetMapping("/today")
    public WeatherForecast getTodayWeather() {
        return weatherService.getTodayWeather();
    }

    // Get the cache statistics
    @GetMapping("/cache/stats")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<CacheStatsDTO> getCacheStats() {
        CacheStatsDTO cacheStats = weatherService.getCacheMetrics();
        return ResponseEntity.ok(cacheStats);
    }

}