package be.kdg.programming6.project.restaurantmanagement.port.out;

import be.kdg.programming6.project.restaurantmanagement.domain.Dish;
import be.kdg.programming6.project.restaurantmanagement.domain.Restaurant;
import be.kdg.programming6.project.restaurantmanagement.domain.valueobject.RestaurantId;

public interface UpdateDishPort {

    Dish changeStateDish(Dish dish);

    Dish updateDish(RestaurantId restaurantId, Dish dish);

}
