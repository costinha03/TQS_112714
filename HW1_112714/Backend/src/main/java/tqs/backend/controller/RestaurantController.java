package tqs.backend.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tqs.backend.dto.RestaurantDTO;
import tqs.backend.entity.Restaurant;
import tqs.backend.service.RestaurantService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

   private final RestaurantService restaurantService;
    private final Logger logger = org.slf4j.LoggerFactory.getLogger(RestaurantController.class);
    @Autowired
    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
        return ResponseEntity.ok(restaurantService.getAllRestaurants());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> getRestaurant(@PathVariable UUID id) {
        return restaurantService.getRestaurantById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public ResponseEntity<Restaurant> createRestaurant(@RequestParam String name) {
        Restaurant restaurantNew = new Restaurant();
        // set the name
        restaurantNew.setName(name);
        Restaurant createdRestaurant = restaurantService.saveRestaurant(restaurantNew);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRestaurant);
    }

@PostMapping("/{restaurantId}/associate-menu")
        public ResponseEntity<?> associateMenuToDay(
                @PathVariable UUID restaurantId,
                @RequestParam Long menuId,
                @RequestParam String dayOfWeek) {
            try {
                restaurantService.associateMenuToDay(restaurantId, menuId, dayOfWeek);
                return ResponseEntity.ok("Menu associated to day successfully");
            } catch (IllegalArgumentException e) {
                logger.error("Error associating menu to day: {}", e.getMessage());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            } catch (Exception e) {
                logger.error("Unexpected error: {}", e.getMessage(), e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
            }
        }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable UUID id) {
        restaurantService.deleteRestaurant(id);
        return ResponseEntity.noContent().build();
    }

}
