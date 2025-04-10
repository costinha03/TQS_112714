package tqs.backend.connection;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import tqs.backend.entity.WeatherForecast;

import java.time.LocalDate;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class WeatherControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void whenGetWeather_thenReturnWeeklyForecast() {
        given()
                .when().get("/api/weather")
                .then()
                .statusCode(200)
                .body("$", hasSize(greaterThanOrEqualTo(0)))
                .body("[0].date", notNullValue())
                .body("[0].weatherCode", notNullValue());
    }

    @Test
    void whenGetTodayWeather_thenReturnTodayForecast() {
        given()
                .when().get("/api/weather/today")
                .then()
                .statusCode(200)
                .body("date", equalTo(LocalDate.now().toString()))
                .body("weatherCode", notNullValue());
    }
}