package be.kdg.programming6.project.ordermanagement.port.out;

import be.kdg.programming6.project.ordermanagement.domain.DishProjection;
import be.kdg.programming6.project.ordermanagement.domain.valueobject.DishId;

import java.util.Optional;

public interface LoadDishProjectionPort {

    Optional<DishProjection> loadDishProjectionById(DishId dishId);
}
