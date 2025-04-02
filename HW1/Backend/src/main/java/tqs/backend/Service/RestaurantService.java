package tqs.backend.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tqs.backend.Entity.Restaurant;
import tqs.backend.Repositories.RestaurantRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    public Optional<Restaurant> getRestaurantById(UUID id) {
        return restaurantRepository.findById(id);
    }

    public Restaurant createRestaurant(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    public Optional<Restaurant> updateRestaurant(UUID id, Restaurant restaurantDetails) {
        return restaurantRepository.findById(id).map(existingRestaurant -> {
            existingRestaurant.setName(restaurantDetails.getName());
            existingRestaurant.setLocation(restaurantDetails.getLocation());
            existingRestaurant.setDescription(restaurantDetails.getDescription());
            existingRestaurant.setCapacity(restaurantDetails.getCapacity());
            existingRestaurant.setOpeningTime(restaurantDetails.getOpeningTime());
            existingRestaurant.setClosingTime(restaurantDetails.getClosingTime());
            existingRestaurant.setIsActive(restaurantDetails.getIsActive());
            return restaurantRepository.save(existingRestaurant);
        });
    }

    public boolean deleteRestaurant(UUID id) {
        if (restaurantRepository.existsById(id)) {
            restaurantRepository.deleteById(id);
            return true;
        }
        return false;
    }



}
