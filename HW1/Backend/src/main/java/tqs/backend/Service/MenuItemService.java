package tqs.backend.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tqs.backend.Entity.MenuItem;
import tqs.backend.Repositories.MenuItemRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MenuItemService {

    private final MenuItemRepository menuItemRepository;

    @Autowired
    public MenuItemService(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    public List<MenuItem> getAllMenuItems() {
        return menuItemRepository.findAll();
    }

    public Optional<MenuItem> getMenuItemById(UUID id) {
        return menuItemRepository.findById(id);
    }

    public List<MenuItem> getMenuItemsByType(MenuItem.MenuItemType type) {
        return menuItemRepository.findByType(type);
    }

    public List<MenuItem> searchMenuItems(String query) {
        return menuItemRepository.findByNameContainingIgnoreCase(query);
    }

    public MenuItem createMenuItem(MenuItem menuItem) {
        return menuItemRepository.save(menuItem);
    }

    public Optional<MenuItem> updateMenuItem(UUID id, MenuItem menuItemDetails) {
        return menuItemRepository.findById(id).map(existingMenuItem -> {
            existingMenuItem.setName(menuItemDetails.getName());
            existingMenuItem.setDescription(menuItemDetails.getDescription());
            existingMenuItem.setPrice(menuItemDetails.getPrice());
            existingMenuItem.setType(menuItemDetails.getType());
            existingMenuItem.setDietaryInfo(menuItemDetails.getDietaryInfo());
            existingMenuItem.setAvailability(menuItemDetails.getAvailability());
            return menuItemRepository.save(existingMenuItem);
        });
    }

    public boolean deleteMenuItem(UUID id) {
        if (menuItemRepository.existsById(id)) {
            menuItemRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
