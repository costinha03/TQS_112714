package tqs.backend.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "weather_forecasts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherForecast {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private LocalDate date;

    @Column(nullable = false)
    private Double temperature;

    @Column(nullable = false)
    private String condition;

    private Double humidity;

    @Column(name = "wind_speed")
    private Double windSpeed;

    @Column(name = "fetched_at", nullable = false)
    private LocalDateTime fetchedAt;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    @PrePersist
    protected void onCreate() {
        this.fetchedAt = LocalDateTime.now();
        // Default TTL: 6 hours
        this.expiresAt = this.fetchedAt.plusHours(6);
    }
}