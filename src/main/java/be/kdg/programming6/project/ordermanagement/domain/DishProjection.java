package be.kdg.programming6.project.ordermanagement.domain;
import be.kdg.programming6.project.ordermanagement.domain.valueobject.DishId;
import be.kdg.programming6.project.ordermanagement.domain.valueobject.DishState;

import java.math.BigDecimal;

public class DishProjection {

    private DishId dishId;
    private BigDecimal price;
    private DishState state;

    public DishProjection(DishId dishId, BigDecimal price, DishState state) {
        this.dishId = dishId;
        this.price = price;
        this.state = state;
    }

    public DishProjection(DishId dishId, DishState state) {
        this.dishId = dishId;
        this.state = state;
    }

    public DishProjection(DishId dishId, BigDecimal price) {
        this.dishId = dishId;
        this.price = price;
    }

    public boolean isAvailable() {
        return state == DishState.PUBLISHED;
    }

    public void changeAvailability(DishState state) {
        this.state = state;
    }

    public void changePrice(BigDecimal price) {
        this.price = price;
    }

    public DishId getDishId() {
        return dishId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public DishState getState() {
        return state;
    }
}
