package tqs.backend.service;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tqs.backend.dto.CacheStatsDTO;
import tqs.backend.dto.WeatherResponse;
import tqs.backend.entity.WeatherForecast;
import tqs.backend.repository.WeatherRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class WeatherService {
    private static final Logger logger = LoggerFactory.getLogger(WeatherService.class);

    private final RestTemplate restTemplate;
    private final WeatherRepository weatherRepository;

    private static final String WEATHER_API_URL =
            "https://api.open-meteo.com/v1/forecast?latitude=40.6443&longitude=-8.6455&daily=weather_code,temperature_2m_min,temperature_2m_max,precipitation_probability_max&timezone=auto";

    public WeatherService(RestTemplate restTemplate, WeatherRepository weatherRepository) {
        this.restTemplate = restTemplate;
        this.weatherRepository = weatherRepository;
    }

    private long totalRequests;
    private long cacheHits;
    private long cacheMisses;

    // Runs automatically when the app starts, ensuring today's weather is available and in first place
    @PostConstruct
    public void updateWeatherOnStartup() {
        LocalDate today = LocalDate.now();
        LocalDate endOfWeek = today.plusDays(6);
        logger.info("Checking weather data for week ending on: {}", endOfWeek);

        if (weatherRepository.findById(endOfWeek).isEmpty()) {
            logger.info("No data found for the upcoming week. Updating weather data.");
            LocalDate yesterday = today.minusDays(1);
            weatherRepository.findById(yesterday).ifPresent(weatherRepository::delete);
            updateWeather();
        } else {
            logger.info("Weather data already exists for this week. Skipping update.");
        }
    }


    private void updateWeather() {
        try {
            logger.info("Fetching weather data from Open-Meteo API...");
            WeatherResponse response = restTemplate.getForObject(WEATHER_API_URL, WeatherResponse.class);

            if (response != null && response.getDaily() != null) {
                List<String> dates = response.getDaily().getTime();
                List<Integer> weatherCodes = response.getDaily().getWeather_code();
                List<Double> minTemps = response.getDaily().getTemperature_2m_min();
                List<Double> maxTemps = response.getDaily().getTemperature_2m_max();
                List<Integer> precipitation = response.getDaily().getPrecipitation_probability_max();

                for (int i = 0; i < dates.size(); i++) {
                    LocalDate date = LocalDate.parse(dates.get(i));

                    WeatherForecast forecast = new WeatherForecast(
                            date,
                            weatherCodes.get(i),
                            minTemps.get(i),
                            maxTemps.get(i),
                            precipitation.get(i)
                    );

                    weatherRepository.save(forecast);
                    logger.info("Saved weather for {}: Temp {}°C to {}°C, Code {}, Rain Prob {}%",
                            date, minTemps.get(i), maxTemps.get(i), weatherCodes.get(i), precipitation.get(i));
                }

                logger.info("Weather update completed successfully.");
            } else {
                logger.warn("Received empty or invalid weather response from Open-Meteo.");
            }
        } catch (Exception e) {
            logger.error("Error while updating weather data: {}", e.getMessage(), e);
        }
    }

    public List<WeatherForecast> getWeeklyWeather() {
        totalRequests++;
        List<WeatherForecast> forecasts = weatherRepository.findAll();
        if (forecasts.isEmpty()) {
            cacheMisses++;
            updateWeather();
            return weatherRepository.findAll();
        }
        cacheHits++;
        logger.info("Fetching weekly weather data from database...");
        return forecasts;
    }

    public WeatherForecast getTodayWeather() {
        totalRequests++;
        LocalDate today = LocalDate.now();
        logger.info("Fetching today's weather data from database for date: {}", today);
        WeatherForecast forecast = weatherRepository.findById(today).orElse(null);
        if (forecast == null) {
            cacheMisses++;
            updateWeather();
            return weatherRepository.findById(today).orElse(null);
        }
        cacheHits++;
        return forecast;
    }

    public CacheStatsDTO getCacheMetrics() {
        return new CacheStatsDTO(totalRequests, cacheHits, cacheMisses, 0);
    }

}

