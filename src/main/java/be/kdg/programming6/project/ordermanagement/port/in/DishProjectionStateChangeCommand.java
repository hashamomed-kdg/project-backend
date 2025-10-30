package be.kdg.programming6.project.ordermanagement.port.in;

import be.kdg.programming6.project.ordermanagement.domain.valueobject.DishId;
import be.kdg.programming6.project.ordermanagement.domain.valueobject.DishState;

public record DishProjectionStateChangeCommand(
        DishId dishId,
        DishState state
) {
}
