package be.kdg.programming6.project.restaurantmanagement.port.in;

import be.kdg.programming6.project.restaurantmanagement.domain.valueobject.DishState;
import be.kdg.programming6.project.restaurantmanagement.domain.valueobject.DishType;
import be.kdg.programming6.project.restaurantmanagement.domain.valueobject.FoodTag;
import be.kdg.programming6.project.restaurantmanagement.domain.valueobject.PictureUrl;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

public record CreateDishCommand(
        String name,
        DishType dishType,
        Set<FoodTag> tags,
        String description,
        BigDecimal price,
        PictureUrl pictureUrl,
        DishState state,
        UUID restaurantId

) {
}
