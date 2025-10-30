package be.kdg.programming6.project.ordermanagement.port.out;

import be.kdg.programming6.project.ordermanagement.domain.RestaurantProjection;

public interface SaveRestaurantProjectionPort {

    RestaurantProjection saveRestaurantProjection(RestaurantProjection restaurantProjection);
}
