package be.kdg.programming6.project.ordermanagement.core;

import be.kdg.programming6.project.ordermanagement.domain.RestaurantProjection;
import be.kdg.programming6.project.ordermanagement.port.in.RestaurantProjectionOpeningHoursChanged;
import be.kdg.programming6.project.ordermanagement.port.in.RestaurantProjectionOpeningHoursChangedCommand;
import be.kdg.programming6.project.ordermanagement.port.out.LoadRestaurantProjectionPort;
import be.kdg.programming6.project.ordermanagement.port.out.SaveRestaurantProjectionPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class RestaurantProjectionOpeningHoursChangedImpl implements RestaurantProjectionOpeningHoursChanged {

    private final LoadRestaurantProjectionPort loadRestaurantProjectionPort;
    private final SaveRestaurantProjectionPort saveRestaurantProjectionPort;


    public RestaurantProjectionOpeningHoursChangedImpl(LoadRestaurantProjectionPort loadRestaurantProjectionPort,
                                                       SaveRestaurantProjectionPort saveRestaurantProjectionPort) {
        this.loadRestaurantProjectionPort = loadRestaurantProjectionPort;
        this.saveRestaurantProjectionPort = saveRestaurantProjectionPort;
    }


    @Override
    @Transactional
    public RestaurantProjection project(RestaurantProjectionOpeningHoursChangedCommand command) {
        RestaurantProjection restaurantProjection = loadRestaurantProjectionPort.loadRestaurantProjectionById(command.restaurantId())
                .orElse(new RestaurantProjection(command.restaurantId(), command.openingHours()));
        saveRestaurantProjectionPort.saveRestaurantProjection(restaurantProjection);
        return restaurantProjection;
    }
}
