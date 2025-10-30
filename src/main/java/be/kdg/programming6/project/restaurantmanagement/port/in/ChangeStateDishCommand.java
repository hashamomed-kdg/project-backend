package be.kdg.programming6.project.restaurantmanagement.port.in;

import be.kdg.programming6.project.restaurantmanagement.domain.valueobject.DishState;

import java.util.UUID;

public record ChangeStateDishCommand(
        UUID restaurantId,
        UUID dishId,
        DishState state
) {
}
