package be.kdg.programming6.project.restaurantmanagement.port.in;

import be.kdg.programming6.project.restaurantmanagement.domain.Dish;

import java.util.List;

public interface FindDishesUseCase {
    List<Dish> findAllDishesByRestaurant(FindAllDishesByRestaurantCommand command);
}
