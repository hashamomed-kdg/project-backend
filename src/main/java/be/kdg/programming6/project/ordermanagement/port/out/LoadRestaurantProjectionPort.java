package be.kdg.programming6.project.ordermanagement.port.out;

import be.kdg.programming6.project.ordermanagement.domain.RestaurantProjection;
import be.kdg.programming6.project.ordermanagement.domain.valueobject.RestaurantId;

import java.util.Optional;

public interface LoadRestaurantProjectionPort {

    Optional<RestaurantProjection> loadRestaurantProjectionById(RestaurantId restaurantId);
}
