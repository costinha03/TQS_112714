package tqs.backend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tqs.backend.entity.*;
import tqs.backend.repository.MenuRepository;
import tqs.backend.repository.RestaurantRepository;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
 public class RestaurantServicesTests {

    @Mock
    private RestaurantRepository restaurantRepo;

    @Mock
    private MenuRepository menuRepo;

    @InjectMocks
    private RestaurantService restaurantService;

    private Restaurant testRestaurant;
    private Menu testMenu;
    private UUID restaurantId;
    private Long menuId;

    @BeforeEach
    void setUp() {
        restaurantId = UUID.randomUUID();
        menuId = 1L;

        testRestaurant = new Restaurant();
        testRestaurant.setId(restaurantId);
        testRestaurant.setName("Test Restaurant");
        testRestaurant.setMaxCapacity(100);

        testMenu = new Menu();
        testMenu.setId(menuId);
    }

    @Test
    void whenGetAllRestaurants_thenReturnList() {
        List<Restaurant> expectedRestaurants = Arrays.asList(testRestaurant);
        when(restaurantRepo.findAll()).thenReturn(expectedRestaurants);

        List<Restaurant> actualRestaurants = restaurantService.getAllRestaurants();

        assertThat(actualRestaurants)
                .isNotEmpty()
                .hasSize(1)
                .contains(testRestaurant);
        verify(restaurantRepo).findAll();
    }

    @Test
    void whenGetRestaurantById_thenReturnRestaurant() {
        when(restaurantRepo.findById(restaurantId)).thenReturn(Optional.of(testRestaurant));

        Optional<Restaurant> result = restaurantService.getRestaurantById(restaurantId);

        assertThat(result)
                .isPresent()
                .contains(testRestaurant);
        verify(restaurantRepo).findById(restaurantId);
    }

    @Test
    void whenSaveRestaurant_thenReturnSavedRestaurant() {
        when(restaurantRepo.save(any(Restaurant.class))).thenReturn(testRestaurant);

        Restaurant savedRestaurant = restaurantService.saveRestaurant(testRestaurant);

        assertThat(savedRestaurant).isEqualTo(testRestaurant);
        verify(restaurantRepo).save(testRestaurant);
    }

    @Test
    void whenDeleteRestaurant_thenCallDeleteById() {
        doNothing().when(restaurantRepo).deleteById(restaurantId);

        restaurantService.deleteRestaurant(restaurantId);

        verify(restaurantRepo).deleteById(restaurantId);
    }

    @Test
    void whenAssociateMenuToDay_withValidIds_thenSuccess() {
        when(restaurantRepo.findById(restaurantId)).thenReturn(Optional.of(testRestaurant));
        when(menuRepo.findById(menuId)).thenReturn(Optional.of(testMenu));
        when(restaurantRepo.save(any(Restaurant.class))).thenReturn(testRestaurant);

        assertDoesNotThrow(() ->
                restaurantService.associateMenuToDay(restaurantId, menuId, "MONDAY")
        );

        verify(restaurantRepo).findById(restaurantId);
        verify(menuRepo).findById(menuId);
        verify(restaurantRepo).save(testRestaurant);
    }

    @Test
    void whenAssociateMenuToDay_withInvalidRestaurantId_thenThrowException() {
        when(restaurantRepo.findById(restaurantId)).thenReturn(Optional.empty());
        when(menuRepo.findById(menuId)).thenReturn(Optional.of(testMenu));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> restaurantService.associateMenuToDay(restaurantId, menuId, "MONDAY")
        );

        assertThat(exception.getMessage())
                .isEqualTo("Restaurant with ID " + restaurantId + " not found");
        verify(restaurantRepo).findById(restaurantId);
        verify(restaurantRepo, never()).save(any());
    }

    @Test
    void whenAssociateMenuToDay_withInvalidMenuId_thenThrowException() {
        when(restaurantRepo.findById(restaurantId)).thenReturn(Optional.of(testRestaurant));
        when(menuRepo.findById(menuId)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> restaurantService.associateMenuToDay(restaurantId, menuId, "MONDAY")
        );

        assertThat(exception.getMessage())
                .isEqualTo("Menu with ID " + menuId + " not found");
        verify(restaurantRepo).findById(restaurantId);
        verify(menuRepo).findById(menuId);
        verify(restaurantRepo, never()).save(any());
    }
}
