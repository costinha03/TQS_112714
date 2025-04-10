package tqs.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Menu {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "meat_dish_id")
    private Dish meatDish;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fish_dish_id")
    private Dish fishDish;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "vegetarian_dish_id")
    private Dish vegetarianDish;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Dish getMeatDish() {
        return meatDish;
    }

    public void setMeatDish(Dish meatDish) {
        this.meatDish = meatDish;
    }

    public Dish getFishDish() {
        return fishDish;
    }

    public void setFishDish(Dish fishDish) {
        this.fishDish = fishDish;
    }

    public Dish getVegetarianDish() {
        return vegetarianDish;
    }

    public void setVegetarianDish(Dish vegetarianDish) {
        this.vegetarianDish = vegetarianDish;
    }

    public BigDecimal getDishPrice(Dish.DishType dishType) {
        switch (dishType) {
            case MEAT:
                return meatDish.getPrice();
            case FISH:
                return fishDish.getPrice();
            case VEGETARIAN:
                return vegetarianDish.getPrice();
            default:
                throw new IllegalArgumentException("Invalid dish type: " + dishType);
        }
    }
}
