package be.kdg.programming6.project.restaurantmanagement.port.in;

import be.kdg.programming6.project.restaurantmanagement.domain.valueobject.RestaurantId;

import java.util.UUID;

public record MarkOrderReadyCommand(
        UUID orderId,
        RestaurantId restaurantId
) {
}
