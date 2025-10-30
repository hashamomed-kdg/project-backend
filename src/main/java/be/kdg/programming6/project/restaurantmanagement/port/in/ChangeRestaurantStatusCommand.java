package be.kdg.programming6.project.restaurantmanagement.port.in;

import be.kdg.programming6.project.restaurantmanagement.domain.valueobject.RestaurantStatus;

import java.util.UUID;

public record ChangeRestaurantStatusCommand(
        UUID restaurantId,
        RestaurantStatus manualStatus
) {
}
