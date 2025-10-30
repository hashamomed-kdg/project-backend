package be.kdg.programming6.project.restaurantmanagement.adapter.in.request;

import java.math.BigDecimal;
import java.util.Set;

public record CreateDishRequest(
        String name,
        String dishType,
        Set<String> foodTags,
        String description,
        BigDecimal price,
        String pictureUrl,
        String state
) {
}
