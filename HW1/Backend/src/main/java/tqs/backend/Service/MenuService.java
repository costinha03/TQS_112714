package tqs.backend.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tqs.backend.Entity.Menu;
import tqs.backend.Entity.MenuItem;
import tqs.backend.Entity.Restaurant;
import tqs.backend.Repositories.MenuRepository;
import tqs.backend.Repositories.RestaurantRepository;

import java.time.LocalDate;
import java.util.*;

@Service
public class MenuService {

    private final MenuRepository menuRepository;
    private final RestaurantRepository restaurantRepository;
    private final WeatherService weatherService;

    @Autowired
    public MenuService(MenuRepository menuRepository,
                       RestaurantRepository restaurantRepository,
                       WeatherService weatherService) {
        this.menuRepository = menuRepository;
        this.restaurantRepository = restaurantRepository;
        this.weatherService = weatherService;
    }

    public List<Menu> getAllMenus() {
        return menuRepository.findAll();
    }

    public Optional<Menu> getMenuById(UUID id) {
        return menuRepository.findById(id);
    }

    public Optional<Menu> getMenuForRestaurantAndDate(UUID restaurantId, LocalDate date) {
        return menuRepository.findByRestaurantIdAndDate(restaurantId, date);
    }

    public List<Menu> getWeeklyMenuForRestaurant(UUID restaurantId) {
        LocalDate today = LocalDate.now();
        LocalDate endDate = today.plusDays(6);
        return menuRepository.findByRestaurantIdAndDateBetween(restaurantId, today, endDate);
    }

    public Menu createMenu(UUID restaurantId, LocalDate date, Set<MenuItem> menuItems) {
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(restaurantId);
        if (restaurantOptional.isEmpty()) {
            throw new IllegalArgumentException("Restaurant not found with ID: " + restaurantId);
        }

        Optional<Menu> existingMenu = menuRepository.findByRestaurantIdAndDate(restaurantId, date);
        if (existingMenu.isPresent()) {
            throw new IllegalStateException("Menu already exists for this restaurant and date");
        }

        Menu menu = new Menu();
        menu.setRestaurant(restaurantOptional.get());
        menu.setDate(date);
        menu.setMenuItems(menuItems);

        return menuRepository.save(menu);
    }

    public Optional<Menu> updateMenu(UUID id, Set<MenuItem> menuItems) {
        return menuRepository.findById(id).map(existingMenu -> {
            existingMenu.setMenuItems(menuItems);
            return menuRepository.save(existingMenu);
        });
    }

    public boolean deleteMenu(UUID id) {
        if (menuRepository.existsById(id)) {
            menuRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

