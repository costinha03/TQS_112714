package tqs.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tqs.backend.dto.DishDTO;
import tqs.backend.dto.MenuDTO;
import tqs.backend.entity.Dish;
import tqs.backend.entity.Menu;
import tqs.backend.entity.Restaurant;
import tqs.backend.repository.DishRepository;
import tqs.backend.repository.MenuRepository;
import tqs.backend.repository.RestaurantRepository;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private DishRepository dishRepository;

    public Menu saveMenu(Menu menu) {
        return menuRepository.save(menu);
    }

    // find all menus
    public Iterable<Menu> findAll() {
        return menuRepository.findAll();
    }

    // create menu


    // update menu
    public Menu updateMenu(Long id, MenuDTO menu) {
        Optional<Menu> existingMenu = menuRepository.findById(id);
        if (existingMenu.isPresent()) {
            Menu updatedMenu = existingMenu.get();

            // Convert DishDTO to Dish entity
            Dish meatDish = createDishFromDTO(menu.getMeatDish());
            Dish fishDish = createDishFromDTO(menu.getFishDish());
            Dish vegetarianDish = createDishFromDTO(menu.getVegetarianDish());

            // Update the menu with the converted dishes
            updatedMenu.setMeatDish(meatDish);
            updatedMenu.setFishDish(fishDish);
            updatedMenu.setVegetarianDish(vegetarianDish);

            return menuRepository.save(updatedMenu);
        } else {
            throw new IllegalArgumentException("Menu with ID " + id + " not found");
        }
    }

    public Menu createMenuFromDTO(MenuDTO menuDTO) {
        // Validate that menuDTO and its components are not null
        if (menuDTO == null) {
            throw new IllegalArgumentException("MenuDTO cannot be null");
        }
        if (menuDTO.getMeatDish() == null || menuDTO.getFishDish() == null || menuDTO.getVegetarianDish() == null) {
            throw new IllegalArgumentException("All dishes in MenuDTO must be provided");
        }

        // Debug prints to check what's coming in
        System.out.println("Meat dish name: " + menuDTO.getMeatDish().getName());
        System.out.println("Fish dish name: " + menuDTO.getFishDish().getName());
        System.out.println("Vegetarian dish name: " + menuDTO.getVegetarianDish().getName());

        // Convert DishDTO to Dish entity with better error handling
        Dish meatDish = createDishFromDTO(menuDTO.getMeatDish());
        Dish fishDish = createDishFromDTO(menuDTO.getFishDish());
        Dish vegetarianDish = createDishFromDTO(menuDTO.getVegetarianDish());

        // Create the Menu entity
        Menu menu = new Menu();
        menu.setMeatDish(meatDish);
        menu.setFishDish(fishDish);
        menu.setVegetarianDish(vegetarianDish);

        return menuRepository.save(menu);
    }

    // Helper method to create a Dish from DishDTO with validation
    private Dish createDishFromDTO(DishDTO dishDTO) {
        if (dishDTO == null) {
            throw new IllegalArgumentException("DishDTO cannot be null");
        }
        if (dishDTO.getName() == null || dishDTO.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Dish name cannot be null or empty");
        }

        if (dishDTO.getType() == null || dishDTO.getType().trim().isEmpty()) {
            throw new IllegalArgumentException("Dish type cannot be null or empty");
        }

        return new Dish(dishDTO.getName(), BigDecimal.valueOf(dishDTO.getPrice()), dishDTO.getType());
    }
    // delete
    public void deleteMenu(Long id) {
        Optional<Menu> menu = menuRepository.findById(id);
        if (menu.isPresent()) {
            menuRepository.delete(menu.get());
        } else {
            throw new IllegalArgumentException("Menu with ID " + id + " not found");
        }
    }
}
