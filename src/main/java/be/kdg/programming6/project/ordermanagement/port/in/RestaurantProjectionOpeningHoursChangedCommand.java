package be.kdg.programming6.project.ordermanagement.port.in;

import be.kdg.programming6.project.ordermanagement.domain.valueobject.OpeningHours;
import be.kdg.programming6.project.ordermanagement.domain.valueobject.RestaurantId;

public record RestaurantProjectionOpeningHoursChangedCommand(
        RestaurantId restaurantId,
        OpeningHours openingHours
) {
}
