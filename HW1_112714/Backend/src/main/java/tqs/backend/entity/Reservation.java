package tqs.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
public class Reservation {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private String studentId;

    @Column(nullable = false)
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TimeSlot timeSlot;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Dish.DishType dishType;

    public Reservation(String studentId, LocalDate date, TimeSlot timeSlot, Dish.DishType dishType, UUID restaurantId, Restaurant.DayOfWeek dayOfWeek, double totalPrice) {
        this.studentId = studentId;
        this.date = date;
        this.timeSlot = timeSlot;
        this.dishType = dishType;
        this.restaurantId = restaurantId;
        this.dayOfWeek = dayOfWeek;
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

    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(TimeSlot timeSlot) {
        this.timeSlot = timeSlot;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Dish.DishType getDishType() {
        return dishType;
    }

    public void setDishType(Dish.DishType dishType) {
        this.dishType = dishType;
    }

    public UUID getRestaurant() {
        return restaurantId;
    }

    public void setRestaurant(UUID restaurant) {
        this.restaurantId = restaurant;
    }

    @Column(name = "restaurant_id", nullable = false)
    private UUID restaurantId;


    // totalPrice
    @Column(nullable = false)
    private double totalPrice;

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Restaurant.DayOfWeek dayOfWeek;

    public Restaurant.DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(Restaurant.DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public enum TimeSlot {
        LUNCH, DINNER
    }

    public enum Status {
        RESERVED, CHECKED_IN, CANCELLED
    }

    public Reservation (){
    }
}
