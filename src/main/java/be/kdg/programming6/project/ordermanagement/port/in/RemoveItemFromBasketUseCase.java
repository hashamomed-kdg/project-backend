package be.kdg.programming6.project.ordermanagement.port.in;

import be.kdg.programming6.project.ordermanagement.domain.Basket;

public interface RemoveItemFromBasketUseCase {

    Basket removeItemFromBasket(RemoveItemFromBasketCommand command);
}
