package tqs.backend.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tqs.backend.Entity.Reservation;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, UUID> {
    Optional<Reservation> findByToken(String token);
    List<Reservation> findByRestaurantIdAndDate(UUID restaurantId, LocalDate date);
    List<Reservation> findByRestaurantIdAndDateAndStatus(UUID restaurantId, LocalDate date, Reservation.ReservationStatus status);
    long countByRestaurantIdAndDateAndMealTimeAndStatus(UUID restaurantId, LocalDate date, Reservation.MealTime mealTime, Reservation.ReservationStatus status);
}

