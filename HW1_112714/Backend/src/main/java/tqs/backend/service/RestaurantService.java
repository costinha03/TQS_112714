package tqs.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tqs.backend.entity.Menu;
import tqs.backend.entity.Restaurant;
import tqs.backend.repository.MenuRepository;
import tqs.backend.repository.RestaurantRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service

public class RestaurantService {

    private final RestaurantRepository restaurantRepo;
    private final MenuRepository menuRepo;


    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepo, MenuRepository menuRepo) {
        this.restaurantRepo = restaurantRepo;
        this.menuRepo = menuRepo;
    }
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepo.findAll();
    }


    public Optional<Restaurant> getRestaurantById(UUID id) {
        return restaurantRepo.findById(id);
    }


    public Restaurant saveRestaurant(Restaurant restaurant) {
        return restaurantRepo.save(restaurant);
    }

    public void deleteRestaurant(UUID id) {
        restaurantRepo.deleteById(id);
    }

    public void associateMenuToDay(UUID restaurantId, Long menuId, String dayOfWeek) {

        Optional<Restaurant> restaurantOptional = restaurantRepo.findById(restaurantId);
        Optional<Menu> menuOptional = menuRepo.findById(menuId);
        if (restaurantOptional.isPresent() && menuOptional.isPresent()) {
            Restaurant restaurant = restaurantOptional.get();
            Menu menu = menuOptional.get();
            restaurant.setMenuForDay(dayOfWeek, menu);
            restaurantRepo.save(restaurant);
        } else {
            if (restaurantOptional.isEmpty()) {
                throw new IllegalArgumentException("Restaurant with ID " + restaurantId + " not found");
            }
            else  {
                throw new IllegalArgumentException("Menu with ID " + menuId + " not found");
            }
        }
    }

}
