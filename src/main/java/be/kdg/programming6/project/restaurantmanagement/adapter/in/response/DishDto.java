package be.kdg.programming6.project.restaurantmanagement.adapter.in.response;

import java.math.BigDecimal;

import java.util.Set;
import java.util.UUID;

public record DishDto(
        UUID dishId,
        String name,
        String dishType,
        Set<String> foodTags,
        String description,
        BigDecimal price,
        String pictureUrl,
        String state
) {}
