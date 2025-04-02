package tqs.backend.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tqs.backend.Entity.MenuItem;

import java.util.List;
import java.util.UUID;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, UUID> {
    List<MenuItem> findByType(MenuItem.MenuItemType type);
    List<MenuItem> findByNameContainingIgnoreCase(String name);
    List<MenuItem> findByRestaurantId(UUID restaurantId);
}
