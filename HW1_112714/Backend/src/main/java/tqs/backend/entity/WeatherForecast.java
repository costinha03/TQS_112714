package tqs.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.LocalDate;

@Entity
public class WeatherForecast {
    @Id
    private LocalDate date;

    private int weatherCode;
    private double minTemperature;
    private double maxTemperature;
    private int precipitationProbability;

    public WeatherForecast() {}

    public WeatherForecast(LocalDate date, int weatherCode, double minTemperature, double maxTemperature, int precipitationProbability) {
        this.date = date;
        this.weatherCode = weatherCode;
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
        this.precipitationProbability = precipitationProbability;
    }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public int getWeatherCode() { return weatherCode; }
    public void setWeatherCode(int weatherCode) { this.weatherCode = weatherCode; }

    public double getMinTemperature() { return minTemperature; }
    public void setMinTemperature(double minTemperature) { this.minTemperature = minTemperature; }

    public double getMaxTemperature() { return maxTemperature; }
    public void setMaxTemperature(double maxTemperature) { this.maxTemperature = maxTemperature; }

    public int getPrecipitationProbability() { return precipitationProbability; }
    public void setPrecipitationProbability(int precipitationProbability) { this.precipitationProbability = precipitationProbability; }
}
