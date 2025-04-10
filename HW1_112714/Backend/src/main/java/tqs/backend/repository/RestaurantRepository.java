package tqs.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tqs.backend.entity.Restaurant;

import java.util.UUID;

public interface RestaurantRepository extends JpaRepository<Restaurant, UUID> {
}
