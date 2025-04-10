package tqs.backend.dto;

import lombok.*;
import tqs.backend.entity.Dish;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class DishDTO {
    private String name;
    private double price;
    private String type; // Can be "MEAT", "FISH", or "VEGETARIAN"

    public String getName() {
        return name;
    }

    public DishDTO(String name, double price, String type) {
        this.name = name;
        this.price = price;
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
