package tqs.backend.entity;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;


public class EntityUnitTest {

    @Test
    public void testReservation() {
        // Create a new Reservation object
        Reservation reservation = new Reservation();

        // Set the properties of the reservation
        reservation.setToken("12345");
        reservation.setStudentId("student123");
        reservation.setDate(LocalDate.now());
        reservation.setTimeSlot(Reservation.TimeSlot.LUNCH);
        reservation.setStatus(Reservation.Status.CHECKED_IN);
        reservation.setDishType(Dish.DishType.MEAT);

        // Assert that the properties are set correctly
        assertEquals("12345", reservation.getToken());
        assertEquals("student123", reservation.getStudentId());
        assertEquals(LocalDate.now(), reservation.getDate());
        assertEquals(Reservation.TimeSlot.LUNCH, reservation.getTimeSlot());
        assertEquals(Reservation.Status.CHECKED_IN, reservation.getStatus());
        assertEquals(Dish.DishType.MEAT, reservation.getDishType());
    }

    @Test
    public void testRestaurant() {
        // Create a new Restaurant object
        Restaurant restaurant = new Restaurant();

        // Set the properties of the restaurant
        restaurant.setName("Test Restaurant");
        restaurant.setMaxCapacity(50);

        // Assert that the properties are set correctly
        assertEquals("Test Restaurant", restaurant.getName());
        assertEquals(50, restaurant.getMaxCapacity());
    }

    @Test
    public void testDish() {
        // Create a new Dish object
        Dish dish = new Dish();

        // Set the properties of the dish
        dish.setName("Test Dish");
        dish.setPrice(new BigDecimal("9.99"));
        dish.setType(Dish.DishType.VEGETARIAN);

        // Assert that the properties are set correctly
        assertEquals("Test Dish", dish.getName());
        assertEquals(new BigDecimal("9.99"), dish.getPrice());
        assertEquals(Dish.DishType.VEGETARIAN, dish.getType());
    }

    @Test
    public void testMenu() {
        // Create a new Menu object
        Menu menu = new Menu();

        // Create dishes
       Dish meatDish = new Dish();
        meatDish.setName("Steak");
        meatDish.setPrice(new BigDecimal("15.99"));
        meatDish.setType(Dish.DishType.MEAT);

        Dish fishDish = new Dish();
        fishDish.setName("Salmon");
        fishDish.setPrice(new BigDecimal("12.99"));
        fishDish.setType(Dish.DishType.FISH);

        Dish vegetarianDish = new Dish();
        vegetarianDish.setName("Veggie Burger");
        vegetarianDish.setPrice(new BigDecimal("9.99"));
        vegetarianDish.setType(Dish.DishType.VEGETARIAN);

        // Add dishes to the menu
        menu.setMeatDish(meatDish);
        menu.setFishDish(fishDish);
        menu.setVegetarianDish(vegetarianDish);

        menu.setId(1L);

        // Assert that the dishes are set correctly
        assertEquals("Steak", menu.getMeatDish().getName());
        assertEquals(new BigDecimal("15.99"), menu.getMeatDish().getPrice());
        assertEquals(Dish.DishType.MEAT, menu.getMeatDish().getType());

        assertEquals("Salmon", menu.getFishDish().getName());
        assertEquals(new BigDecimal("12.99"), menu.getFishDish().getPrice());
        assertEquals(Dish.DishType.FISH, menu.getFishDish().getType());

        assertEquals(1L, menu.getId());

        assertEquals("Veggie Burger", menu.getVegetarianDish().getName());
        assertEquals(new BigDecimal("9.99"), menu.getVegetarianDish().getPrice());
        assertEquals(Dish.DishType.VEGETARIAN, menu.getVegetarianDish().getType());
    }

    @Test
    public void testRestaurantMenuManagement() {
        Restaurant restaurant = new Restaurant();
        Menu mondayMenu = new Menu();
        Menu tuesdayMenu = new Menu();

        // Test setting menus for different days
        restaurant.setMenuForDay("MONDAY", mondayMenu);
        restaurant.setMenuForDay("TUESDAY", tuesdayMenu);

        // Test getting menus
        assertEquals(mondayMenu, restaurant.getMenuForDay("MONDAY"));
        assertEquals(tuesdayMenu, restaurant.getMenuForDay("TUESDAY"));

        // Test invalid day input
        assertThrows(IllegalArgumentException.class, () ->
            restaurant.setMenuForDay("INVALIDDAY", mondayMenu));

        // Test null/empty day input
        assertThrows(IllegalArgumentException.class, () ->
            restaurant.getMenuForDay(null));
        assertThrows(IllegalArgumentException.class, () ->
            restaurant.getMenuForDay(""));

        // Test getting menu for day with no menu set
        assertThrows(IllegalArgumentException.class, () ->
            restaurant.getMenuForDay("WEDNESDAY"));
    }

    @Test
    public void testReservationCapacityManagement() {
        Restaurant restaurant = new Restaurant();
        LocalDate today = LocalDate.now();

        // Test initial capacity
        assertEquals(20, restaurant.getMaxCapacity());
        assertTrue(restaurant.canAcceptReservation(today, 5));

        // Test incrementing reservation counts
        restaurant.incrementReservationCount(today);
        restaurant.incrementReservationCount(today);

        assertEquals(2, restaurant.getReservationCounts().get(today));

        // Test capacity limits
        assertTrue(restaurant.canAcceptReservation(today, 17)); // 2 + 17 < 20
        assertFalse(restaurant.canAcceptReservation(today, 19)); // 2 + 19 > 20

        // Test different dates
        LocalDate tomorrow = today.plusDays(1);
        assertTrue(restaurant.canAcceptReservation(tomorrow, 15)); // New date starts at 0
    }

    @Test
    public void testRestaurantProperties() {
        Restaurant restaurant = new Restaurant();
        UUID id = UUID.randomUUID();
        List<Reservation> reservations = new ArrayList<>();
        Map<LocalDate, Integer> reservationCounts = new HashMap<>();
        Map<Restaurant.DayOfWeek, Menu> menus = new HashMap<>();

        // Test all setters
        restaurant.setId(id);
        restaurant.setName("Updated Restaurant");
        restaurant.setReservations(reservations);
        restaurant.setReservationCounts(reservationCounts);
        restaurant.setMaxCapacity(30);
        restaurant.setMenus(menus);

        // Test all getters
        assertEquals(id, restaurant.getId());
        assertEquals("Updated Restaurant", restaurant.getName());
        assertEquals(reservations, restaurant.getReservations());
        assertEquals(reservationCounts, restaurant.getReservationCounts());
        assertEquals(30, restaurant.getMaxCapacity());
        assertEquals(menus, restaurant.getMenus());
    }

    @Test
    public void testDayOfWeekEnum() {
        // Test all enum values
        assertEquals(7, Restaurant.DayOfWeek.values().length);
        assertTrue(Arrays.asList(Restaurant.DayOfWeek.values())
            .contains(Restaurant.DayOfWeek.MONDAY));
        assertTrue(Arrays.asList(Restaurant.DayOfWeek.values())
            .contains(Restaurant.DayOfWeek.SUNDAY));
    }

    @Test
    public void testWeatherForecastConstructorAndGetters() {
        LocalDate date = LocalDate.now();
        WeatherForecast forecast = new WeatherForecast(
            date,
            800,  // weather code for clear sky
            15.5, // min temperature
            25.7, // max temperature
            20    // precipitation probability
        );

        assertEquals(date, forecast.getDate());
        assertEquals(800, forecast.getWeatherCode());
        assertEquals(15.5, forecast.getMinTemperature(), 0.001);
        assertEquals(25.7, forecast.getMaxTemperature(), 0.001);
        assertEquals(20, forecast.getPrecipitationProbability());
    }

    @Test
    public void testWeatherForecastSetters() {
        WeatherForecast forecast = new WeatherForecast();
        LocalDate date = LocalDate.now();

        forecast.setDate(date);
        forecast.setWeatherCode(500);  // weather code for rain
        forecast.setMinTemperature(10.0);
        forecast.setMaxTemperature(20.0);
        forecast.setPrecipitationProbability(80);

        assertEquals(date, forecast.getDate());
        assertEquals(500, forecast.getWeatherCode());
        assertEquals(10.0, forecast.getMinTemperature(), 0.001);
        assertEquals(20.0, forecast.getMaxTemperature(), 0.001);
        assertEquals(80, forecast.getPrecipitationProbability());
    }

    @Test
    public void testWeatherForecastEmptyConstructor() {
        WeatherForecast forecast = new WeatherForecast();

        assertNull(forecast.getDate());
        assertEquals(0, forecast.getWeatherCode());
        assertEquals(0.0, forecast.getMinTemperature(), 0.001);
        assertEquals(0.0, forecast.getMaxTemperature(), 0.001);
        assertEquals(0, forecast.getPrecipitationProbability());
    }

    @Test
    public void testWeatherForecastTemperatureRange() {
        WeatherForecast forecast = new WeatherForecast();
        LocalDate tomorrow = LocalDate.now().plusDays(1);

        forecast.setDate(tomorrow);
        forecast.setMinTemperature(-10.5);
        forecast.setMaxTemperature(35.8);

        assertEquals(-10.5, forecast.getMinTemperature(), 0.001);
        assertEquals(35.8, forecast.getMaxTemperature(), 0.001);
    }

    @Test
    public void testWeatherForecastExtremeValues() {
        WeatherForecast forecast = new WeatherForecast();

        // Test extreme values
        forecast.setWeatherCode(Integer.MAX_VALUE);
        forecast.setMinTemperature(Double.MIN_VALUE);
        forecast.setMaxTemperature(Double.MAX_VALUE);
        forecast.setPrecipitationProbability(100);

        assertEquals(Integer.MAX_VALUE, forecast.getWeatherCode());
        assertEquals(Double.MIN_VALUE, forecast.getMinTemperature(), 0.001);
        assertEquals(Double.MAX_VALUE, forecast.getMaxTemperature(), 0.001);
        assertEquals(100, forecast.getPrecipitationProbability());
    }

    @Test
    public void testWeatherForecastFutureDate() {
        WeatherForecast forecast = new WeatherForecast();
        LocalDate futureDate = LocalDate.now().plusDays(5);

        forecast.setDate(futureDate);
        assertEquals(futureDate, forecast.getDate());
    }

    @Test
    public void testMultipleWeatherForecasts() {
        LocalDate today = LocalDate.now();
        WeatherForecast forecast1 = new WeatherForecast(today, 800, 15.0, 25.0, 0);
        WeatherForecast forecast2 = new WeatherForecast(today.plusDays(1), 500, 12.0, 22.0, 70);

        assertNotEquals(forecast1.getDate(), forecast2.getDate());
        assertNotEquals(forecast1.getWeatherCode(), forecast2.getWeatherCode());
        assertNotEquals(forecast1.getPrecipitationProbability(), forecast2.getPrecipitationProbability());
    }

    @Test
    public void testCacheMetricsConstructorAndGetters() {
        CacheMetrics metrics = new CacheMetrics(100, 75, 25);

        assertEquals(100, metrics.getTotalRequests());
        assertEquals(75, metrics.getHits());
        assertEquals(25, metrics.getMisses());
    }

    @Test
    public void testCacheMetricsSetters() {
        CacheMetrics metrics = new CacheMetrics(0, 0, 0);

        metrics.setTotalRequests(200);
        metrics.setHits(150);
        metrics.setMisses(50);

        assertEquals(200, metrics.getTotalRequests());
        assertEquals(150, metrics.getHits());
        assertEquals(50, metrics.getMisses());
    }

    @Test
    public void testCacheMetricsZeroValues() {
        CacheMetrics metrics = new CacheMetrics(0, 0, 0);

        assertEquals(0, metrics.getTotalRequests());
        assertEquals(0, metrics.getHits());
        assertEquals(0, metrics.getMisses());
    }

    @Test
    public void testCacheMetricsMaxValues() {
        CacheMetrics metrics = new CacheMetrics(Long.MAX_VALUE, Long.MAX_VALUE, Long.MAX_VALUE);

        assertEquals(Long.MAX_VALUE, metrics.getTotalRequests());
        assertEquals(Long.MAX_VALUE, metrics.getHits());
        assertEquals(Long.MAX_VALUE, metrics.getMisses());
    }

    @Test
    public void testCacheMetricsNegativeValues() {
        CacheMetrics metrics = new CacheMetrics(-1, -1, -1);

        assertEquals(-1, metrics.getTotalRequests());
        assertEquals(-1, metrics.getHits());
        assertEquals(-1, metrics.getMisses());
    }

    @Test
    public void testCacheMetricsConsistency() {
        CacheMetrics metrics = new CacheMetrics(100, 60, 40);

        // Verify that hits + misses equals total requests
        assertEquals(metrics.getTotalRequests(), metrics.getHits() + metrics.getMisses());
    }


}
