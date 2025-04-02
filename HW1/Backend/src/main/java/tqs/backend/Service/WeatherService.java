package tqs.backend.Service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tqs.backend.Entity.CacheMetrics;
import tqs.backend.Entity.WeatherForecast;
import tqs.backend.Repositories.CacheMetricsRepository;
import tqs.backend.Repositories.WeatherForecastRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WeatherService {

    private static final Logger logger = LoggerFactory.getLogger(WeatherService.class);
    private static final String CACHE_METRICS_ID = "WEATHER_CACHE";

    private final WeatherForecastRepository weatherForecastRepository;
    private final CacheMetricsRepository cacheMetricsRepository;
    private final RestTemplate restTemplate;

    @Value("${weather.api.url}")
    private String weatherApiUrl;

    @Value("${weather.api.key}")
    private String weatherApiKey;

    @Value("${weather.cache.ttl:6}") // Default TTL: 6 hours
    private int cacheTtlHours;

    @Autowired
    public WeatherService(WeatherForecastRepository weatherForecastRepository,
                          CacheMetricsRepository cacheMetricsRepository) {
        this.weatherForecastRepository = weatherForecastRepository;
        this.cacheMetricsRepository = cacheMetricsRepository;
        this.restTemplate = new RestTemplate();

        // Initialize cache metrics if not exist
        if (!cacheMetricsRepository.existsById(CACHE_METRICS_ID)) {
            cacheMetricsRepository.save(new CacheMetrics(CACHE_METRICS_ID, 0L, 0L, 0L));
        }
    }

    public WeatherForecast getWeatherForecast(LocalDate date) {
        CacheMetrics metrics = getCacheMetrics();
        metrics.incrementTotalRequests();

        Optional<WeatherForecast> cachedForecast = weatherForecastRepository.findByDate(date);

        if (cachedForecast.isPresent() && cachedForecast.get().getExpiresAt().isAfter(LocalDateTime.now())) {
            logger.info("Cache hit for weather forecast on date: {}", date);
            metrics.incrementCacheHits();
            cacheMetricsRepository.save(metrics);
            return cachedForecast.get();
        }

        logger.info("Cache miss for weather forecast on date: {}", date);
        metrics.incrementCacheMisses();
        cacheMetricsRepository.save(metrics);

        // Fetch from external API
        WeatherForecast newForecast = fetchWeatherFromExternalApi(date);
        return weatherForecastRepository.save(newForecast);
    }

    public List<WeatherForecast> getWeeklyWeatherForecast() {
        LocalDate today = LocalDate.now();
        List<WeatherForecast> weeklyForecast = new ArrayList<>();

        for (int i = 0; i < 7; i++) {
            LocalDate date = today.plusDays(i);
            weeklyForecast.add(getWeatherForecast(date));
        }

        return weeklyForecast;
    }

    private WeatherForecast fetchWeatherFromExternalApi(LocalDate date) {
        logger.info("Fetching weather forecast for date: {} from external API", date);

        // This is a mock implementation - in a real application, you would call the actual weather API
        // String url = weatherApiUrl + "?date=" + date + "&apiKey=" + weatherApiKey;
        // WeatherApiResponse response = restTemplate.getForObject(url, WeatherApiResponse.class);

        // For demonstration, create a mock forecast
        WeatherForecast forecast = new WeatherForecast();
        forecast.setDate(date);
        forecast.setTemperature(20.0 + Math.random() * 10); // Random temp between 20-30
        forecast.setCondition(getRandomWeatherCondition());
        forecast.setHumidity(60.0 + Math.random() * 20); // Random humidity between 60-80%
        forecast.setWindSpeed(5.0 + Math.random() * 15); // Random wind speed between 5-20 km/h
        forecast.setFetchedAt(LocalDateTime.now());
        forecast.setExpiresAt(LocalDateTime.now().plusHours(cacheTtlHours));

        return forecast;
    }

    private String getRandomWeatherCondition() {
        String[] conditions = {"Sunny", "Partly Cloudy", "Cloudy", "Rainy", "Thunderstorm", "Windy"};
        int index = (int) (Math.random() * conditions.length);
        return conditions[index];
    }

    public CacheMetrics getCacheMetrics() {
        return cacheMetricsRepository.findById(CACHE_METRICS_ID)
                .orElseGet(() -> new CacheMetrics(CACHE_METRICS_ID, 0L, 0L, 0L));
    }

    public void cleanExpiredCache() {
        LocalDateTime now = LocalDateTime.now();
        weatherForecastRepository.deleteByExpiresAtBefore(now);
        logger.info("Cleaned expired weather forecasts from cache");
    }
}