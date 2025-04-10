package tqs.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tqs.backend.entity.Dish;
import tqs.backend.entity.Reservation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
public class ReservationResponseDTO {
    private UUID id;
    private String token;
    private String studentId;
    private LocalDate date;
    private Reservation.TimeSlot timeSlot;
    private Reservation.Status status;
    private UUID restaurantId;
    private String restaurantName;
    private Dish.DishType dishType;
    private BigDecimal totalPrice;

    public ReservationResponseDTO(UUID id, String token, String studentId, LocalDate date, Reservation.TimeSlot timeSlot, Reservation.Status status, UUID restaurantId, String restaurantName, Dish.DishType dishType, BigDecimal totalPrice) {
        this.id = id;
        this.token = token;
        this.studentId = studentId;
        this.date = date;
        this.timeSlot = timeSlot;
        this.status = status;
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.dishType = dishType;
        this.totalPrice = totalPrice;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Reservation.TimeSlot getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(Reservation.TimeSlot timeSlot) {
        this.timeSlot = timeSlot;
    }

    public Reservation.Status getStatus() {
        return status;
    }

    public void setStatus(Reservation.Status status) {
        this.status = status;
    }

    public UUID getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(UUID restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public Dish.DishType getDishType() {
        return dishType;
    }

    public void setDishType(Dish.DishType dishType) {
        this.dishType = dishType;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}