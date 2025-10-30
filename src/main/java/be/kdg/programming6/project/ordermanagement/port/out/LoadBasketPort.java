package be.kdg.programming6.project.ordermanagement.port.out;

import be.kdg.programming6.project.ordermanagement.domain.Basket;
import be.kdg.programming6.project.ordermanagement.domain.valueobject.BasketId;

import java.util.Optional;

public interface LoadBasketPort {

    Optional<Basket> loadBasketById(BasketId basketId);
}
