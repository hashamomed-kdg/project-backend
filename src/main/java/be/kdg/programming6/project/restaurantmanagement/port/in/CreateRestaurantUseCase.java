package be.kdg.programming6.project.restaurantmanagement.port.in;

import be.kdg.programming6.project.restaurantmanagement.domain.Restaurant;

public interface CreateRestaurantUseCase {

    Restaurant createRestaurant(CreateRestaurantCommand command);
}
