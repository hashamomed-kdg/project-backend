package be.kdg.programming6.project.ordermanagement.core;

import be.kdg.programming6.project.ordermanagement.domain.RestaurantProjection;
import be.kdg.programming6.project.ordermanagement.port.in.RestaurantProjectionCreated;
import be.kdg.programming6.project.ordermanagement.port.in.RestaurantProjectionCreatedCommand;
import be.kdg.programming6.project.ordermanagement.port.out.SaveRestaurantProjectionPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class RestaurantProjectionCreatedImpl implements RestaurantProjectionCreated {

    private final SaveRestaurantProjectionPort saveRestaurantProjectionPort;

    public RestaurantProjectionCreatedImpl(SaveRestaurantProjectionPort saveRestaurantProjectionPort) {
        this.saveRestaurantProjectionPort = saveRestaurantProjectionPort;
    }

    @Override
    @Transactional
    public RestaurantProjection project(RestaurantProjectionCreatedCommand command) {
        RestaurantProjection restaurantProjection = new RestaurantProjection(
                command.restaurantId(),
                command.openingHours(),
                command.status());

        saveRestaurantProjectionPort.saveRestaurantProjection(restaurantProjection);
        return restaurantProjection;
    }
}
