package tqs.backend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import tqs.backend.dto.MenuDTO;
import tqs.backend.entity.Dish;
import tqs.backend.entity.Menu;
import tqs.backend.dto.DishDTO;
import tqs.backend.repository.MenuRepository;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class MenuServiceTest {

    @Mock
    private MenuRepository menuRepository;

    @InjectMocks
    private MenuService menuService;

    private Menu testMenu;
    private MenuDTO testMenuDTO;
    private DishDTO meatDishDTO;
    private DishDTO fishDishDTO;
    private DishDTO vegetarianDishDTO;

    @BeforeEach
    void setUp() {
        // Setup test DTOs
        meatDishDTO = new DishDTO("Steak", 15.99, "MEAT");
        fishDishDTO = new DishDTO("Salmon", 14.99, "FISH");
        vegetarianDishDTO = new DishDTO("Veggie Pasta", 12.99, "VEGETARIAN");

        // Setup test MenuDTO
        testMenuDTO = new MenuDTO(meatDishDTO, fishDishDTO, vegetarianDishDTO);

        // Setup test Menu
        testMenu = new Menu();
        testMenu.setId(1L);
        testMenu.setMeatDish(new Dish(meatDishDTO.getName(), BigDecimal.valueOf(meatDishDTO.getPrice()), meatDishDTO.getType()));
        testMenu.setFishDish(new Dish(fishDishDTO.getName(), BigDecimal.valueOf(fishDishDTO.getPrice()), fishDishDTO.getType()));
        testMenu.setVegetarianDish(new Dish(vegetarianDishDTO.getName(), BigDecimal.valueOf(vegetarianDishDTO.getPrice()), vegetarianDishDTO.getType()));
    }

    @Test
    void whenCreateMenuFromDTO_withValidDishes_thenReturnMenu() {
        when(menuRepository.save(any(Menu.class))).thenReturn(testMenu);

        Menu result = menuService.createMenuFromDTO(testMenuDTO);

        assertThat(result).isNotNull();
        assertThat(result.getMeatDish().getName()).isEqualTo(meatDishDTO.getName());
        assertThat(result.getFishDish().getName()).isEqualTo(fishDishDTO.getName());
        assertThat(result.getVegetarianDish().getName()).isEqualTo(vegetarianDishDTO.getName());
        verify(menuRepository).save(any(Menu.class));
    }

    @Test
    void whenCreateMenuFromDTO_withNullMenuDTO_thenThrowException() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> menuService.createMenuFromDTO(null)
        );

        assertThat(exception.getMessage()).isEqualTo("MenuDTO cannot be null");
        verify(menuRepository, never()).save(any());
    }

    @Test
    void whenCreateMenuFromDTO_withNullDishes_thenThrowException() {
        MenuDTO invalidMenuDTO = new MenuDTO(null, null, null);

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> menuService.createMenuFromDTO(invalidMenuDTO)
        );

        assertThat(exception.getMessage()).isEqualTo("All dishes in MenuDTO must be provided");
        verify(menuRepository, never()).save(any());
    }

    @Test
    void whenSaveMenu_thenReturnSavedMenu() {
        when(menuRepository.save(testMenu)).thenReturn(testMenu);

        Menu result = menuService.saveMenu(testMenu);

        assertThat(result).isEqualTo(testMenu);
        verify(menuRepository).save(testMenu);
    }

    @Test
    void whenFindAll_thenReturnAllMenus() {
        List<Menu> expectedMenus = Arrays.asList(testMenu);
        when(menuRepository.findAll()).thenReturn(expectedMenus);

        Iterable<Menu> result = menuService.findAll();

        assertThat(result).containsExactly(testMenu);
        verify(menuRepository).findAll();
    }

    @Test
    void whenUpdateMenu_withExistingId_thenReturnUpdatedMenu() {
        when(menuRepository.findById(1L)).thenReturn(Optional.of(testMenu));
        when(menuRepository.save(any(Menu.class))).thenReturn(testMenu);

        Menu result = menuService.updateMenu(1L, testMenuDTO);

        assertThat(result).isNotNull();
        verify(menuRepository).findById(1L);
        verify(menuRepository).save(any(Menu.class));
    }

    @Test
    void whenUpdateMenu_withNonExistingId_thenThrowException() {
        when(menuRepository.findById(99L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> menuService.updateMenu(99L, testMenuDTO)
        );

        assertThat(exception.getMessage()).isEqualTo("Menu with ID 99 not found");
        verify(menuRepository).findById(99L);
        verify(menuRepository, never()).save(any());
    }

    @Test
    void whenDeleteMenu_withExistingId_thenSuccess() {
        when(menuRepository.findById(1L)).thenReturn(Optional.of(testMenu));
        doNothing().when(menuRepository).delete(testMenu);

        assertDoesNotThrow(() -> menuService.deleteMenu(1L));

        verify(menuRepository).findById(1L);
        verify(menuRepository).delete(testMenu);
    }

    @Test
    void whenDeleteMenu_withNonExistingId_thenThrowException() {
        when(menuRepository.findById(99L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> menuService.deleteMenu(99L)
        );

        assertThat(exception.getMessage()).isEqualTo("Menu with ID 99 not found");
        verify(menuRepository).findById(99L);
        verify(menuRepository, never()).delete(any());
    }
}