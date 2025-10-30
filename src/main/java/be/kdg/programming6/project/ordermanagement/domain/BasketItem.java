package be.kdg.programming6.project.ordermanagement.domain;

import be.kdg.programming6.project.ordermanagement.domain.valueobject.DishId;
import be.kdg.programming6.project.ordermanagement.domain.valueobject.RestaurantId;

import java.math.BigDecimal;

public class BasketItem {

    private DishId dishId;
    private RestaurantId restaurantId;
    private String name;
    private BigDecimal price;
    private long quantity;


    public BasketItem(DishId dishId, RestaurantId restaurantId, String name, BigDecimal price, long quantity) {
        this.dishId = dishId;
        this.restaurantId = restaurantId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public void addQuantity(long quantity) {
        this.quantity += quantity;
    }
    public void removeQuantity(long quantity) {
        this.quantity -= quantity;
        if (this.quantity < 0) {
            this.quantity = 0;
        }
    }

    public void updatePrice(BigDecimal price) {
        this.price = price;
    }


    public BigDecimal getTotalPrice() {
        return price.multiply(BigDecimal.valueOf(quantity));
    }

    public DishId getDishId() {
        return dishId;
    }
    public RestaurantId getRestaurantId() {
        return restaurantId;
    }
    public String getName() {
        return name;
    }
    public BigDecimal getPrice() {
        return price;
    }
    public long getQuantity() {
        return quantity;
    }



}

