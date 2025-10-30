package be.kdg.programming6.project.ordermanagement.port.in;

import be.kdg.programming6.project.ordermanagement.domain.valueobject.BasketId;
import be.kdg.programming6.project.ordermanagement.domain.valueobject.DishId;
import be.kdg.programming6.project.ordermanagement.domain.valueobject.RestaurantId;

import java.math.BigDecimal;

public record AddItemInBasketCommand(
        BasketId basketId,
        DishId dishId,
        RestaurantId restaurantId,
        String name,
        BigDecimal price,
        long quantity
) {
}
