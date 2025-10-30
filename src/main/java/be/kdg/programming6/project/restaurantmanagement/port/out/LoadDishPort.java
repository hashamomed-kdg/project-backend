package be.kdg.programming6.project.restaurantmanagement.port.out;

import be.kdg.programming6.project.restaurantmanagement.domain.Dish;
import be.kdg.programming6.project.restaurantmanagement.domain.valueobject.DishId;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LoadDishPort {

    Optional<Dish> loadById(DishId dishId);

    Optional<Dish> loadByName(String name);

    List<Dish> loadAll();

    List<Dish> loadByRestaurantId(UUID restaurantId);

    List<Dish> loadAllDraftsWithPublishAtBefore(LocalDateTime time);
}
