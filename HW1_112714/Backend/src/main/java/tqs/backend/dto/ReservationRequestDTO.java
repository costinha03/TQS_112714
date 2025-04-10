package tqs.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tqs.backend.entity.Dish;
import tqs.backend.entity.Reservation;
import tqs.backend.entity.Restaurant;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationRequestDTO {
    private UUID restaurantId;
    private String studentId;
    private LocalDate date;
    private Reservation.TimeSlot timeSlot;
    private Dish.DishType dishType;
    private Restaurant.DayOfWeek dayOfWeek;

    public UUID getRestaurantId() {
        return restaurantId;
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

    public Dish.DishType getDishType() {
        return dishType;
    }

    public void setDishType(Dish.DishType dishType) {
        this.dishType = dishType;
    }

    public Restaurant.DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(Restaurant.DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
}

