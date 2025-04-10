package tqs.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Builder
public class Restaurant {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany
    @JoinColumn(name = "restaurant_id", referencedColumnName = "id") // importante!
    private List<Reservation> reservations;

    @ElementCollection
    @CollectionTable(name = "restaurant_reservation_counts", joinColumns = @JoinColumn(name = "restaurant_id"))
    @MapKeyColumn(name = "date")
    @Column(name = "reservation_count")
    private Map<LocalDate, Integer> reservationCounts = new HashMap<>();

    @Column(nullable = false)
    private int maxCapacity = 20;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public Map<LocalDate, Integer> getReservationCounts() {
        return reservationCounts;
    }

    public void setReservationCounts(Map<LocalDate, Integer> reservationCounts) {
        this.reservationCounts = reservationCounts;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public Map<DayOfWeek, Menu> getMenus() {
        return menus;
    }

    public void setMenus(Map<DayOfWeek, Menu> menus) {
        this.menus = menus;
    }

    public Restaurant() {

    }

    public enum DayOfWeek {
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
    }

    @OneToMany
    @MapKeyEnumerated(EnumType.STRING)
    @JoinTable(
            name = "restaurant_menus",
            joinColumns = @JoinColumn(name = "restaurant_id"),
            inverseJoinColumns = @JoinColumn(name = "menu_id")
    )
    @MapKeyColumn(name = "day_of_week")
    private Map<DayOfWeek, Menu> menus = new HashMap<>();

    public void setMenuForDay(String day, Menu menu) {
    try {
        DayOfWeek dayOfWeek = DayOfWeek.valueOf(day.toUpperCase());
        menus.put(dayOfWeek, menu);
    } catch (IllegalArgumentException e) {
        throw new IllegalArgumentException("Invalid day of the week: " + day);
    }
    }

    public Menu getMenuForDay(String day) {
        if (day == null || day.isBlank()) {
            throw new IllegalArgumentException("Day of the week cannot be null or empty");
        }
        try {
            DayOfWeek dayOfWeek = DayOfWeek.valueOf(day.toUpperCase());
            Menu menu = menus.get(dayOfWeek);
            if (menu == null) {
                throw new IllegalArgumentException("No menu found for the day: " + day);
            }
            return menu;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid day of the week: " + day, e);
        }
    }

    public boolean canAcceptReservation(LocalDate date, int count) {
        int currentCount = reservationCounts.getOrDefault(date, 0) + count;
        return currentCount < maxCapacity;
    }

    public void incrementReservationCount(LocalDate date) {
        reservationCounts.put(date, reservationCounts.getOrDefault(date, 0) + 1);
    }


}
