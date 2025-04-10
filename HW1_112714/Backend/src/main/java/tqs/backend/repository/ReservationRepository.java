package tqs.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tqs.backend.entity.Reservation;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, UUID> {
    List<Reservation> findByStudentId(String studentId);
    List<Reservation> findByRestaurantIdAndDate(UUID restaurantId, LocalDate date);


    Optional<Reservation> findByToken(String token);
    List<Reservation> findByRestaurantId(UUID restaurantId);

    List<Reservation> findByDateBefore(LocalDate now);
}