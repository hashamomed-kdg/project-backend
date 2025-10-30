package be.kdg.programming6.project.ordermanagement.port.in;

import be.kdg.programming6.project.ordermanagement.domain.RestaurantProjection;

public interface RestaurantProjectionStatusChanged {

    RestaurantProjection project(RestaurantProjectionStatusChangeCommand command);
}
