package be.kdg.programming6.project.restaurantmanagement.port.out;

import be.kdg.programming6.project.restaurantmanagement.domain.Restaurant;

public interface SaveRestaurantPort {

    Restaurant saveRestaurant(Restaurant restaurant);
}
