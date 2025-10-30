package be.kdg.programming6.project.ordermanagement.port.out;

import be.kdg.programming6.project.ordermanagement.domain.Basket;

public interface SaveBasketPort {

    Basket saveBasket(Basket basket);
}
