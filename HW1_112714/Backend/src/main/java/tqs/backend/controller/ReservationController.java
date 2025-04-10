package tqs.backend.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tqs.backend.dto.ReservationRequestDTO;
import tqs.backend.dto.ReservationResponseDTO;
import tqs.backend.entity.Menu;
import tqs.backend.entity.Reservation;
import tqs.backend.entity.Restaurant;
import tqs.backend.exception.ReservationException;
import tqs.backend.service.ReservationService;
import tqs.backend.service.RestaurantService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/reservations")
@CrossOrigin(origins = "http://localhost:3000")
public class ReservationController {

    private final ReservationService reservationService;
    private final RestaurantService restaurantService;
@DeleteMapping("/{token}")
    public ResponseEntity<?> deleteReservationByToken(@PathVariable String token) {
        try {
            reservationService.deleteByToken(token);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (ReservationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping
        public ResponseEntity<List<ReservationResponseDTO>> findAllReservations() {
            List<Reservation> reservations = reservationService.getAllReservations();
            List<ReservationResponseDTO> responseList = reservations.stream()
                    .map(this::mapToResponseDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(responseList);
        }
    @Autowired
    public ReservationController(ReservationService reservationService, RestaurantService restaurantService) {
        this.reservationService = reservationService;
        this.restaurantService = restaurantService;
    }

    @PostMapping
    public ResponseEntity<?> createReservation(@RequestBody ReservationRequestDTO requestDTO) {
        try {
            // Find restaurant
            Restaurant restaurant = restaurantService.getRestaurantById(requestDTO.getRestaurantId())
                    .orElseThrow(() -> new ReservationException("Restaurant not found"));

            // Fetch menu for the specified day
            Menu menu = restaurant.getMenuForDay(requestDTO.getDayOfWeek().name());

            // Calculate total price based on the selected dish type
            BigDecimal totalPrice = menu.getDishPrice(requestDTO.getDishType());

// Create reservation entity
                    Reservation reservation = new Reservation(
                            requestDTO.getStudentId(),
                            requestDTO.getDate(),
                            requestDTO.getTimeSlot(),
                            requestDTO.getDishType(),
                            restaurant.getId(),
                            requestDTO.getDayOfWeek(),
                            totalPrice.doubleValue() // Set total price
                    );


            // Save reservation
            Reservation createdReservation = reservationService.createReservation(reservation);


            ReservationResponseDTO responseDTO = new ReservationResponseDTO(
                    createdReservation.getId(),
                    createdReservation.getToken(),
                    createdReservation.getStudentId(),
                    createdReservation.getDate(),
                    createdReservation.getTimeSlot(),
                    createdReservation.getStatus(),
                    restaurant.getId(),
                    restaurant.getName(),
                    createdReservation.getDishType(),
                    BigDecimal.valueOf(createdReservation.getTotalPrice())
            );


            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
        } catch (ReservationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<ReservationResponseDTO>> getReservationsByStudentId(@PathVariable String studentId) {

        // Fetch reservations by student ID
    List<Reservation> reservations = reservationService.getReservationsByStudentId(studentId);
        List<ReservationResponseDTO> responseList = reservations.stream()
                .filter(reservation -> "reserved".equalsIgnoreCase(reservation.getStatus().name()))
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseList);
    }




    @GetMapping("/restaurant")
    public ResponseEntity<List<ReservationResponseDTO>> getReservationsByRestaurantId(@Param("restaurantId") UUID restaurantId) {
        List<Reservation> reservations = reservationService.getReservationsByRestaurantId(restaurantId);
        List<ReservationResponseDTO> responseList = reservations.stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/{token}")
    public ResponseEntity<?> getReservationByToken(@PathVariable String token) {
        return reservationService.getReservationByToken(token)
                .map(reservation -> ResponseEntity.ok(mapToResponseDTO(reservation)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{token}/check-in")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> checkInReservation(@PathVariable String token) {
        try {
            Reservation checkedInReservation = reservationService.checkInReservation(token);
            return ResponseEntity.ok(mapToResponseDTO(checkedInReservation));
        } catch (ReservationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/{token}/cancel")
    public ResponseEntity<?> cancelReservation(@PathVariable String token) {
        try {
            Reservation cancelledReservation = reservationService.cancelReservation(token);
            return ResponseEntity.ok(mapToResponseDTO(cancelledReservation));
        } catch (ReservationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    private ReservationResponseDTO mapToResponseDTO(Reservation createdReservation) {
        // Fetch the restaurant using the restaurant ID from the reservation
        Restaurant restaurant = restaurantService.getRestaurantById(createdReservation.getRestaurant())
                .orElseThrow(() -> new ReservationException("Restaurant not found"));

        return new ReservationResponseDTO(
                createdReservation.getId(),
                createdReservation.getToken(),
                createdReservation.getStudentId(),
                createdReservation.getDate(),
                createdReservation.getTimeSlot(),
                createdReservation.getStatus(),
                restaurant.getId(),
                restaurant.getName(),
                createdReservation.getDishType(),
                BigDecimal.valueOf(createdReservation.getTotalPrice()) // Include totalPrice
        );
    }

}