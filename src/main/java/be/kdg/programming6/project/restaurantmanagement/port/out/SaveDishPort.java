package be.kdg.programming6.project.restaurantmanagement.port.out;

import be.kdg.programming6.project.restaurantmanagement.domain.Dish;
import be.kdg.programming6.project.restaurantmanagement.domain.valueobject.RestaurantId;

public interface SaveDishPort {

    Dish saveDish(RestaurantId restaurantId, Dish dish);

    Dish createDraft(Dish dish);
}
