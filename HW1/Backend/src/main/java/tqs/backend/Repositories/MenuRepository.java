package tqs.backend.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tqs.backend.Entity.Menu;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MenuRepository extends JpaRepository<Menu, UUID> {
    List<Menu> findByRestaurantId(UUID restaurantId);
    List<Menu> findByRestaurantIdAndDateBetween(UUID restaurantId, LocalDate startDate, LocalDate endDate);
    Optional<Menu> findByRestaurantIdAndDate(UUID restaurantId, LocalDate date);
}
