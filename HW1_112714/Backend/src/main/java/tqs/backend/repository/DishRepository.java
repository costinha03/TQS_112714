package tqs.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tqs.backend.entity.Dish;

public interface DishRepository extends JpaRepository<Dish, Long> {
}
