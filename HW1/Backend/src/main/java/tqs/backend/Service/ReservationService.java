package tqs.backend.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tqs.backend.Entity.MenuItem;
import tqs.backend.Entity.Reservation;
import tqs.backend.Entity.Restaurant;
import tqs.backend.Repositories.MenuItemRepository;
import tqs.backend.Repositories.ReservationRepository;
import tqs.backend.Repositories.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReservationService {
    private static final Logger logger = LoggerFactory.getLogger(ReservationService.class);

    private final ReservationRepository reservationRepository;
    private final RestaurantRepository restaurantRepository;
    private final MenuItemRepository menuItemRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository,
                              RestaurantRepository restaurantRepository,
                              MenuItemRepository menuItemRepository) {
        this.reservationRepository = reservationRepository;
        this.restaurantRepository = restaurantRepository;
        this.menuItemRepository = menuItemRepository;
    }

    public Optional<Reservation> getReservationByToken(String token) {
        return reservationRepository.findByToken(token);
    }

    public List<Reservation> getReservationsForRestaurantAndDate(UUID restaurantId, LocalDate date) {
        return reservationRepository.findByRestaurantIdAndDate(restaurantId, date);
    }

    public Reservation createReservation(UUID restaurantId, LocalDate date,
                                         Reservation.MealTime mealTime, UUID menuItemId,
                                         Integer numberOfPeople) {

        // Validate restaurant exists
        Optional<Restaurant> restaurantOpt = restaurantRepository.findById(restaurantId);
        if (restaurantOpt.isEmpty()) {
            throw new IllegalArgumentException("Restaurant not found with ID: " + restaurantId);
        }

        Restaurant restaurant = restaurantOpt.get();

        // Check restaurant capacity
        long activeReservations = reservationRepository.countByRestaurantIdAndDateAndMealTimeAndStatus(
                restaurantId, date, mealTime, Reservation.ReservationStatus.ACTIVE);

        if (activeReservations + numberOfPeople > restaurant.getCapacity()) {
            throw new IllegalStateException("Restaurant capacity exceeded for the selected date and meal time");
        }

        // Create new reservation
        Reservation reservation = new Reservation();
        reservation.setRestaurant(restaurant);
        reservation.setDate(date);
        reservation.setMealTime(mealTime);
        reservation.setNumberOfPeople(numberOfPeople);
        reservation.setStatus(Reservation.ReservationStatus.ACTIVE);

        // Set menu item if provided
        if (menuItemId != null) {
            Optional<MenuItem> menuItemOpt = menuItemRepository.findById(menuItemId);
            menuItemOpt.ifPresent(reservation::setMenuItem);
        }

        // Generate unique token
        reservation.setToken(generateUniqueToken());

        logger.info("Creating new reservation for restaurant {} on date {} for {} people",
                restaurantId, date, numberOfPeople);

        return reservationRepository.save(reservation);
    }

    public Optional<Reservation> cancelReservation(String token) {
        return reservationRepository.findByToken(token).map(reservation -> {
            if (reservation.getStatus() != Reservation.ReservationStatus.ACTIVE) {
                throw new IllegalStateException("Reservation is not active and cannot be cancelled");
            }

            reservation.setStatus(Reservation.ReservationStatus.CANCELLED);
            logger.info("Cancelling reservation with token: {}", token);

            return reservationRepository.save(reservation);
        });
    }

    public Optional<Reservation> checkInReservation(String token) {
        return reservationRepository.findByToken(token).map(reservation -> {
            if (reservation.getStatus() != Reservation.ReservationStatus.ACTIVE) {
                throw new IllegalStateException("Reservation is not active and cannot be checked in");
            }

            if (!reservation.getDate().equals(LocalDate.now())) {
                throw new IllegalStateException("Reservation is for a different date");
            }

            reservation.setStatus(Reservation.ReservationStatus.USED);
            logger.info("Checking in reservation with token: {}", token);

            return reservationRepository.save(reservation);
        });
    }

    private String generateUniqueToken() {
        // Simple implementation - in production, use a more robust method
        String token;
        do {
            token = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        } while (reservationRepository.findByToken(token).isPresent());

        return token;
    }
}
