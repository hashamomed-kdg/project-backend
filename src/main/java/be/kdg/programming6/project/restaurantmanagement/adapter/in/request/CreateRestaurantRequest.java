package be.kdg.programming6.project.restaurantmanagement.adapter.in.request;

import be.kdg.programming6.project.restaurantmanagement.adapter.in.request.dtos.AddressDto;
import be.kdg.programming6.project.restaurantmanagement.adapter.in.request.dtos.OpeningHoursDto;

import java.util.List;
import java.util.UUID;

public record CreateRestaurantRequest(
        UUID owner,
        String name,
        AddressDto address,
        String email,
        String cuisineType,
        List<String> pictures,
        double defaultPreparationTime,
        OpeningHoursDto openingHours
) {}
