package tqs.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tqs.backend.dto.MenuDTO;
import tqs.backend.entity.Menu;
import tqs.backend.service.MenuService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/menus")
public class MenuController {

    @Autowired
    private MenuService menuService;

    private static final Logger logger = LoggerFactory.getLogger(MenuController.class);

    @PostMapping
    public ResponseEntity<?> createMenu(@RequestBody MenuDTO menuDTO) {
        logger.info("Received MenuDTO: {}", menuDTO); // Log incoming request body

        try {
            // Convert MenuDTO to Menu entity and handle dish creation
            Menu menu = menuService.createMenuFromDTO(menuDTO);
            logger.info("Converted MenuDTO to Menu entity: {}", menu); // Log the converted Menu entity
            // Save the menu to the database
            Menu savedMenu = menuService.saveMenu(menu);

            return new ResponseEntity<>(savedMenu, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            // Handle validation errors with a more specific message
            logger.error("Validation error creating menu: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            // Handle other unexpected errors
            logger.error("Error creating menu: {}", e.getMessage(), e);
            return new ResponseEntity<>("An unexpected error occurred: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllMenus() {
        try {
            return new ResponseEntity<>(menuService.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error retrieving menus: {}", e.getMessage(), e);
            return new ResponseEntity<>("An unexpected error occurred: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMenu(@PathVariable Long id) {
        try {
            menuService.deleteMenu(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error("Error deleting menu: {}", e.getMessage(), e);
            return new ResponseEntity<>("An unexpected error occurred: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateMenu(@PathVariable Long id, @RequestBody MenuDTO menuDTO) {
        try {
            Menu updatedMenu = menuService.updateMenu(id, menuDTO);
            return new ResponseEntity<>(updatedMenu, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            logger.error("Validation error updating menu: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("Error updating menu: {}", e.getMessage(), e);
            return new ResponseEntity<>("An unexpected error occurred: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    // delete all
    @DeleteMapping("/all")
    public ResponseEntity<?> deleteAllMenus() {
        try {
            menuService.findAll().forEach(menu -> menuService.deleteMenu(menu.getId()));
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error("Error deleting all menus: {}", e.getMessage(), e);
            return new ResponseEntity<>("An unexpected error occurred: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
