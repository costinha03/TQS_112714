package tqs.backend.dto;

import lombok.*;

import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantDTO {

    private UUID id;
    private String name;
    private Map<String, Long> menusByDay;

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

    public Map<String, Long> getMenusByDay() {
        return menusByDay;
    }

    public void setMenusByDay(Map<String, Long> menusByDay) {
        this.menusByDay = menusByDay;
    }
}

