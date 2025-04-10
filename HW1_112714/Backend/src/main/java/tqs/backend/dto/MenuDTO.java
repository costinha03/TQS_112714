package tqs.backend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class MenuDTO {
    private DishDTO meatDish;
    private DishDTO fishDish;
    private DishDTO vegetarianDish;


    public MenuDTO(DishDTO meatDish, DishDTO fishDish, DishDTO vegetarianDish) {
        this.meatDish = meatDish;
        this.fishDish = fishDish;
        this.vegetarianDish = vegetarianDish;
    }
    public DishDTO getMeatDish() {
        return meatDish;
    }

    public void setMeatDish(DishDTO meatDish) {
        this.meatDish = meatDish;
    }

    public DishDTO getFishDish() {
        return fishDish;
    }

    public void setFishDish(DishDTO fishDish) {
        this.fishDish = fishDish;
    }

    public DishDTO getVegetarianDish() {
        return vegetarianDish;
    }

    public void setVegetarianDish(DishDTO vegetarianDish) {
        this.vegetarianDish = vegetarianDish;
    }
}
