package tqs.backend.service;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tqs.backend.entity.Dish;
import tqs.backend.entity.Reservation;
import tqs.backend.entity.Restaurant;
import tqs.backend.exception.ReservationException;
import tqs.backend.repository.ReservationRepository;
import tqs.backend.repository.RestaurantRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private static final Logger log = LoggerFactory.getLogger(ReservationService.class);
    private final ReservationRepository reservationRepository;
    private final RestaurantRepository restaurantRepository;
    private final RestaurantService restaurantService;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, RestaurantRepository restaurantRepository, RestaurantService restaurantService) {
        this.reservationRepository = reservationRepository;
        this.restaurantRepository = restaurantRepository;
        this.restaurantService = restaurantService;
    }
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }
        @Transactional
        public Reservation createReservation(Reservation reservation) {
            // Check if restaurant exists
            log.info("Reservation: {}", reservation);
            Restaurant restaurant = restaurantRepository.findById(reservation.getRestaurant())
                    .orElseThrow(() -> new ReservationException("Restaurant not found"));

            // Generate a unique token
            String token = reservation.getDate().format(DateTimeFormatter.ofPattern("ddMMyyyy")) + reservation.getStudentId();
            reservation.setToken(token);
            log.info("Generated token: {}", reservation.getToken());

            // Set initial status to RESERVED
            reservation.setStatus(Reservation.Status.RESERVED);

            // Calculate total price (example logic, adjust as needed)
            double basePrice = 10.0; // Example base price
            double dishTypeMultiplier = reservation.getDishType() == Dish.DishType.VEGETARIAN ? 1.0 : 1.5;
            double timeSlotMultiplier = reservation.getTimeSlot() == Reservation.TimeSlot.LUNCH ? 1.0 : 1.2;
            double totalPrice = basePrice * dishTypeMultiplier * timeSlotMultiplier;
            reservation.setTotalPrice(totalPrice); // Assuming a `setTotalPrice` method exists

            // Increment reservation count for the date
            restaurant.incrementReservationCount(reservation.getDate());
            restaurantRepository.save(restaurant);

            // Save the reservation
            log.info("Saving reservation: {}", reservation);
            return reservationRepository.save(reservation);
        }


    public List<Reservation> getReservationsByStudentId(String studentId) {
        return reservationRepository.findByStudentId(studentId);
    }


    public Optional<Reservation> getReservationByToken(String token) {
        return reservationRepository.findByToken(token);
    }

    @Transactional
    public Reservation checkInReservation(String token) {
        Reservation reservation = reservationRepository.findByToken(token)
                .orElseThrow(() -> new ReservationException("Reservation not found"));

        // Verify reservation is for today
        if (!reservation.getDate().equals(LocalDate.now())) {
            throw new ReservationException("Check-in is only allowed on the reservation date");
        }

        // Update status to CHECKED_IN
        reservation.setStatus(Reservation.Status.CHECKED_IN);
        return reservationRepository.save(reservation);
    }

    @Transactional
    public List<Reservation> getReservationsByRestaurantId(UUID restaurantId) {
        return reservationRepository.findByRestaurantId(restaurantId);
    }

    @Transactional
    public Reservation cancelReservation(String token) {
        Reservation reservation = reservationRepository.findByToken(token)
                .orElseThrow(() -> new ReservationException("Reservation not found"));

        // Check if reservation is already checked in
        if (reservation.getStatus() == Reservation.Status.CHECKED_IN) {
            throw new ReservationException("Cannot cancel a checked-in reservation");
        }

        // Update status to CANCELLED
        reservation.setStatus(Reservation.Status.CANCELLED);

        // Decrement reservation count for the restaurant
        UUID restaurantId = reservation.getRestaurant();
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ReservationException("Restaurant not found"));
        LocalDate date = reservation.getDate();
        int currentCount = restaurant.getReservationCounts().getOrDefault(date, 0);
        if (currentCount > 0) {
            restaurant.getReservationCounts().put(date, currentCount - 1);
            restaurantRepository.save(restaurant);
        }

        return reservationRepository.save(reservation);
    }

    @PostConstruct
    public void deleteExpiredReservations() {
        List<Reservation> expiredReservations = reservationRepository.findByDateBefore(LocalDate.now());
        if (!expiredReservations.isEmpty()) {
            reservationRepository.deleteAll(expiredReservations);
            System.out.println("Deleted expired reservations: " + expiredReservations.size());
        }
    }


    @Transactional
    public void deleteByToken(String token) {
        Reservation reservation = reservationRepository.findByToken(token)
                .orElseThrow(() -> new ReservationException("Reservation not found"));

        // Decrement reservation count for the restaurant
        Restaurant restaurant = restaurantRepository.findById(reservation.getRestaurant())
                .orElseThrow(() -> new ReservationException("Restaurant not found"));
        LocalDate date = reservation.getDate();
        int currentCount = restaurant.getReservationCounts().getOrDefault(date, 0);

        // Always update and save the restaurant, even if count is 0
        restaurant.getReservationCounts().put(date, Math.max(0, currentCount - 1));
        restaurantRepository.save(restaurant);

        reservationRepository.delete(reservation);
    }
}