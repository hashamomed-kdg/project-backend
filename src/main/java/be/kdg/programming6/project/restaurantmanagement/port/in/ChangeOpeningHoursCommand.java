package be.kdg.programming6.project.restaurantmanagement.port.in;

import be.kdg.programming6.project.restaurantmanagement.domain.valueobject.OpeningHours;

import java.util.UUID;

public record ChangeOpeningHoursCommand(
        UUID restaurantId,
        OpeningHours openingHours
) {
}
