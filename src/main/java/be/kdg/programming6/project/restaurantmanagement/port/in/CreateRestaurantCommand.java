package be.kdg.programming6.project.restaurantmanagement.port.in;

import be.kdg.programming6.project.restaurantmanagement.domain.valueobject.*;

import java.util.List;

public record CreateRestaurantCommand(
        OwnerId owner,
        String restaurantName,
        Address address,
        EmailAddress emailAddress,
        List<PictureUrl> pictures,
        CuisineType cuisineType,
        double defaultPreparationTime,
        OpeningHours openingHours
) {
}
