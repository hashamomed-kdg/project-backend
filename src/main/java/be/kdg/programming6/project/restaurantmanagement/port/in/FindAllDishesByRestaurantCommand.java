package be.kdg.programming6.project.restaurantmanagement.port.in;

import be.kdg.programming6.project.restaurantmanagement.domain.valueobject.RestaurantId;

public record FindAllDishesByRestaurantCommand(
        RestaurantId restaurantId
) {
}
