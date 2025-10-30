package be.kdg.programming6.project.ordermanagement.port.in;

import be.kdg.programming6.project.ordermanagement.domain.valueobject.BasketId;
import be.kdg.programming6.project.ordermanagement.domain.valueobject.DishId;

public record RemoveItemFromBasketCommand(
        DishId dishId,
        BasketId basketId
) {
}
