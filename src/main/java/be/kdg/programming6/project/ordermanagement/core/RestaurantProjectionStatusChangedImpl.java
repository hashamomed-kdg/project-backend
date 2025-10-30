package be.kdg.programming6.project.ordermanagement.core;

import be.kdg.programming6.project.ordermanagement.domain.RestaurantProjection;
import be.kdg.programming6.project.ordermanagement.port.in.RestaurantProjectionStatusChangeCommand;
import be.kdg.programming6.project.ordermanagement.port.in.RestaurantProjectionStatusChanged;
import be.kdg.programming6.project.ordermanagement.port.out.LoadRestaurantProjectionPort;
import be.kdg.programming6.project.ordermanagement.port.out.SaveRestaurantProjectionPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class RestaurantProjectionStatusChangedImpl implements RestaurantProjectionStatusChanged {

    private final LoadRestaurantProjectionPort loadRestaurantProjectionPort;
    private final SaveRestaurantProjectionPort saveRestaurantProjectionPort;

    public RestaurantProjectionStatusChangedImpl(LoadRestaurantProjectionPort loadRestaurantProjectionPort, SaveRestaurantProjectionPort saveRestaurantProjectionPort) {
        this.loadRestaurantProjectionPort = loadRestaurantProjectionPort;
        this.saveRestaurantProjectionPort = saveRestaurantProjectionPort;
    }


    @Override
    @Transactional
    public RestaurantProjection project(RestaurantProjectionStatusChangeCommand command) {
        RestaurantProjection restaurantProjection = loadRestaurantProjectionPort.loadRestaurantProjectionById(command.restaurantId())
                .orElse(new RestaurantProjection(command.restaurantId(), command.status()));
        saveRestaurantProjectionPort.saveRestaurantProjection(restaurantProjection);
        return restaurantProjection;
    }
}
