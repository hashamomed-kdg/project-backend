package be.kdg.programming6.project.ordermanagement.port.in;

import be.kdg.programming6.project.ordermanagement.domain.valueobject.RestaurantId;
import be.kdg.programming6.project.ordermanagement.domain.valueobject.RestaurantStatus;

public record RestaurantProjectionStatusChangeCommand(
        RestaurantId restaurantId,
        RestaurantStatus status
) {
}
