package be.kdg.programming6.project.restaurantmanagement.core;

import be.kdg.programming6.project.restaurantmanagement.domain.Restaurant;
import be.kdg.programming6.project.restaurantmanagement.domain.valueobject.RestaurantId;
import be.kdg.programming6.project.restaurantmanagement.port.in.ChangeRestaurantStatusCommand;
import be.kdg.programming6.project.restaurantmanagement.port.in.ChangeRestaurantStatusUseCase;
import be.kdg.programming6.project.restaurantmanagement.port.out.LoadRestaurantPort;
import be.kdg.programming6.project.restaurantmanagement.port.out.UpdateRestaurantPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ChangeRestaurantStatusUseCaseImpl implements ChangeRestaurantStatusUseCase {

    private final LoadRestaurantPort loadRestaurantPort;
    private final List<UpdateRestaurantPort> updateRestaurantPort;

    public ChangeRestaurantStatusUseCaseImpl(LoadRestaurantPort loadRestaurantPort, List<UpdateRestaurantPort> updateRestaurantPort) {
        this.loadRestaurantPort = loadRestaurantPort;
        this.updateRestaurantPort = updateRestaurantPort;
    }


    @Override
    @Transactional
    public Restaurant changeRestaurantStatus(ChangeRestaurantStatusCommand command) {
        Restaurant restaurant = loadRestaurantPort.loadById(RestaurantId.of(command.restaurantId())).get();
        restaurant.changeManualStatus(command.manualStatus());
        updateRestaurantPort.forEach(port -> port.updateRestaurant(restaurant));
        return restaurant;
    }
}
