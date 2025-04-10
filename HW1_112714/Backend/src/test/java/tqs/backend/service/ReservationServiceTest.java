package tqs.backend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tqs.backend.entity.Dish;
import tqs.backend.entity.Reservation;
import tqs.backend.entity.Restaurant;
import tqs.backend.exception.ReservationException;
import tqs.backend.repository.ReservationRepository;
import tqs.backend.repository.RestaurantRepository;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private RestaurantService restaurantService;

    @InjectMocks
    private ReservationService reservationService;

    private Restaurant restaurant;
    private Reservation reservation;
    private static final UUID RESTAURANT_ID = UUID.randomUUID();
    private static final String STUDENT_ID = "A12345";
    private static final String TOKEN = "01012024A12345";

    @BeforeEach
    void setUp() {
        restaurant = new Restaurant();
        restaurant.setId(RESTAURANT_ID);
        restaurant.setReservationCounts(new HashMap<>());

        reservation = new Reservation();
        reservation.setRestaurant(RESTAURANT_ID);
        reservation.setStudentId(STUDENT_ID);
        reservation.setDate(LocalDate.now());
        reservation.setDishType(Dish.DishType.MEAT);
        reservation.setTimeSlot(Reservation.TimeSlot.LUNCH);
    }

    @Test
    void whenGetAllReservations_thenReturnAllReservations() {
        List<Reservation> expectedReservations = Arrays.asList(reservation);
        when(reservationRepository.findAll()).thenReturn(expectedReservations);

        List<Reservation> actualReservations = reservationService.getAllReservations();

        assertThat(actualReservations).isEqualTo(expectedReservations);
        verify(reservationRepository).findAll();
    }

    @Test
    void whenCreateReservation_thenSuccess() {
        when(restaurantRepository.findById(RESTAURANT_ID)).thenReturn(Optional.of(restaurant));
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);

        Reservation created = reservationService.createReservation(reservation);

        assertThat(created).isNotNull();
        assertThat(created.getStatus()).isEqualTo(Reservation.Status.RESERVED);
        verify(restaurantRepository).save(restaurant);
        verify(reservationRepository).save(any(Reservation.class));
    }

    @Test
    void whenCreateReservation_withNonExistentRestaurant_thenThrowException() {
        when(restaurantRepository.findById(RESTAURANT_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> reservationService.createReservation(reservation))
                .isInstanceOf(ReservationException.class)
                .hasMessage("Restaurant not found");
    }

    @Test
    void whenGetReservationsByStudentId_thenReturnReservations() {
        List<Reservation> expectedReservations = Arrays.asList(reservation);
        when(reservationRepository.findByStudentId(STUDENT_ID)).thenReturn(expectedReservations);

        List<Reservation> actualReservations = reservationService.getReservationsByStudentId(STUDENT_ID);

        assertThat(actualReservations).isEqualTo(expectedReservations);
        verify(reservationRepository).findByStudentId(STUDENT_ID);
    }

    @Test
    void whenGetReservationByToken_thenReturnReservation() {
        when(reservationRepository.findByToken(TOKEN)).thenReturn(Optional.of(reservation));

        Optional<Reservation> found = reservationService.getReservationByToken(TOKEN);

        assertThat(found).isPresent();
        assertThat(found.get()).isEqualTo(reservation);
        verify(reservationRepository).findByToken(TOKEN);
    }

    @Test
    void whenCheckInReservation_thenStatusUpdated() {
        reservation.setStatus(Reservation.Status.RESERVED);
        when(reservationRepository.findByToken(TOKEN)).thenReturn(Optional.of(reservation));
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);

        Reservation checkedIn = reservationService.checkInReservation(TOKEN);

        assertThat(checkedIn.getStatus()).isEqualTo(Reservation.Status.CHECKED_IN);
        verify(reservationRepository).save(reservation);
    }

    @Test
    void whenGetReservationsByRestaurantId_thenReturnReservations() {
        List<Reservation> expectedReservations = Arrays.asList(reservation);
        when(reservationRepository.findByRestaurantId(RESTAURANT_ID)).thenReturn(expectedReservations);

        List<Reservation> actualReservations = reservationService.getReservationsByRestaurantId(RESTAURANT_ID);

        assertThat(actualReservations).isEqualTo(expectedReservations);
        verify(reservationRepository).findByRestaurantId(RESTAURANT_ID);
    }

    @Test
    void whenCancelReservation_thenStatusUpdated() {
        // Setup
        LocalDate today = LocalDate.now();
        reservation.setStatus(Reservation.Status.RESERVED);
        reservation.setDate(today);

        // Set up a non-zero reservation count for the date
        Map<LocalDate, Integer> reservationCounts = new HashMap<>();
        reservationCounts.put(today, 1); // Set a count of 1 for today
        restaurant.setReservationCounts(reservationCounts);

        when(reservationRepository.findByToken(TOKEN)).thenReturn(Optional.of(reservation));
        when(restaurantRepository.findById(RESTAURANT_ID)).thenReturn(Optional.of(restaurant));
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);

        // Execute
        Reservation cancelled = reservationService.cancelReservation(TOKEN);

        // Verify
        assertThat(cancelled.getStatus()).isEqualTo(Reservation.Status.CANCELLED);
        verify(restaurantRepository).findById(RESTAURANT_ID);
        verify(restaurantRepository).save(restaurant);
        verify(reservationRepository).save(reservation);
    }

    @Test
    void whenDeleteByToken_thenSuccess() {
        LocalDate today = LocalDate.now();
        reservation.setStatus(Reservation.Status.RESERVED);
        reservation.setDate(today);

        // Set up a non-zero reservation count for the date
        Map<LocalDate, Integer> reservationCounts = new HashMap<>();
        reservationCounts.put(today, 1); // Set a count of 1 for today
        restaurant.setReservationCounts(reservationCounts);

        when(reservationRepository.findByToken(TOKEN)).thenReturn(Optional.of(reservation));
        when(restaurantRepository.findById(RESTAURANT_ID)).thenReturn(Optional.of(restaurant));

        reservationService.deleteByToken(TOKEN);

        verify(reservationRepository).delete(reservation);
        verify(restaurantRepository).save(restaurant);
    }
}