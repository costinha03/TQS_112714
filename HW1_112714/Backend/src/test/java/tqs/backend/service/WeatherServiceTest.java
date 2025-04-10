package tqs.backend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;
import tqs.backend.dto.CacheStatsDTO;
import tqs.backend.dto.WeatherResponse;
import tqs.backend.entity.WeatherForecast;
import tqs.backend.repository.WeatherRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WeatherServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private WeatherRepository weatherRepository;

    @InjectMocks
    private WeatherService weatherService;

    private WeatherForecast mockForecast;
    private WeatherResponse mockResponse;

    @BeforeEach
    void setUp() {
        // Setup mock forecast
        mockForecast = new WeatherForecast(
                LocalDate.now(),
                800,
                15.0,
                25.0,
                10
        );

        // Setup mock API response
        mockResponse = new WeatherResponse();
        WeatherResponse.DailyWeather daily = new WeatherResponse.DailyWeather();

        List<String> dates = Arrays.asList(LocalDate.now().toString());
        List<Integer> weatherCodes = Arrays.asList(800);
        List<Double> minTemps = Arrays.asList(15.0);
        List<Double> maxTemps = Arrays.asList(25.0);
        List<Integer> precipProbs = Arrays.asList(10);

        daily.setTime(dates);
        daily.setWeather_code(weatherCodes);
        daily.setTemperature_2m_min(minTemps);
        daily.setTemperature_2m_max(maxTemps);
        daily.setPrecipitation_probability_max(precipProbs);

        mockResponse.setDaily(daily);
    }

    @Test
    void whenGetWeeklyWeather_withDataInDb_thenReturnForecasts() {
        List<WeatherForecast> mockForecasts = Arrays.asList(mockForecast);
        when(weatherRepository.findAll()).thenReturn(mockForecasts);

        List<WeatherForecast> result = weatherService.getWeeklyWeather();

        assertThat(result).isEqualTo(mockForecasts);
        verify(weatherRepository, times(1)).findAll();
        verify(restTemplate, never()).getForObject(anyString(), any());
    }

    @Test
    void whenGetWeeklyWeather_withEmptyDb_thenUpdateAndReturnForecasts() {
        when(weatherRepository.findAll())
            .thenReturn(List.of())
            .thenReturn(Arrays.asList(mockForecast));
        when(restTemplate.getForObject(anyString(), eq(WeatherResponse.class)))
            .thenReturn(mockResponse);

        List<WeatherForecast> result = weatherService.getWeeklyWeather();

        assertThat(result).hasSize(1);
        verify(weatherRepository, times(2)).findAll();
        verify(restTemplate, times(1)).getForObject(anyString(), eq(WeatherResponse.class));
    }

    @Test
    void whenGetTodayWeather_withDataInDb_thenReturnForecast() {
        when(weatherRepository.findById(LocalDate.now())).thenReturn(Optional.of(mockForecast));

        WeatherForecast result = weatherService.getTodayWeather();

        assertThat(result).isEqualTo(mockForecast);
        verify(weatherRepository, times(1)).findById(any(LocalDate.class));
        verify(restTemplate, never()).getForObject(anyString(), any());
    }

    @Test
    void whenGetTodayWeather_withNoData_thenUpdateAndReturnForecast() {
        when(weatherRepository.findById(LocalDate.now()))
            .thenReturn(Optional.empty())
            .thenReturn(Optional.of(mockForecast));
        when(restTemplate.getForObject(anyString(), eq(WeatherResponse.class)))
            .thenReturn(mockResponse);

        WeatherForecast result = weatherService.getTodayWeather();

        assertThat(result).isEqualTo(mockForecast);
        verify(weatherRepository, times(2)).findById(any(LocalDate.class));
        verify(restTemplate, times(1)).getForObject(anyString(), eq(WeatherResponse.class));
    }

    @Test
    void whenUpdateWeatherOnStartup_withNoData_thenUpdateWeather() {
        when(weatherRepository.findById(any(LocalDate.class))).thenReturn(Optional.empty());
        when(restTemplate.getForObject(anyString(), eq(WeatherResponse.class)))
            .thenReturn(mockResponse);

        weatherService.updateWeatherOnStartup();

        verify(restTemplate, times(1)).getForObject(anyString(), eq(WeatherResponse.class));
        verify(weatherRepository, times(1)).save(any(WeatherForecast.class));
    }

    @Test
    void whenUpdateWeatherOnStartup_withExistingData_thenSkipUpdate() {
        when(weatherRepository.findById(any(LocalDate.class))).thenReturn(Optional.of(mockForecast));

        weatherService.updateWeatherOnStartup();

        verify(restTemplate, never()).getForObject(anyString(), eq(WeatherResponse.class));
        verify(weatherRepository, never()).save(any(WeatherForecast.class));
    }

}