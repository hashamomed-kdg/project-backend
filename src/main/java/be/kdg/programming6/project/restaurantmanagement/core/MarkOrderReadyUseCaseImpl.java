package be.kdg.programming6.project.restaurantmanagement.core;

import be.kdg.programming6.project.restaurantmanagement.domain.Restaurant;
import be.kdg.programming6.project.restaurantmanagement.port.in.MarkOrderReadyCommand;
import be.kdg.programming6.project.restaurantmanagement.port.in.MarkOrderReadyUseCase;
import be.kdg.programming6.project.restaurantmanagement.port.out.LoadRestaurantPort;
import be.kdg.programming6.project.restaurantmanagement.port.out.UpdateRestaurantPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarkOrderReadyUseCaseImpl implements MarkOrderReadyUseCase {

    private final LoadRestaurantPort loadRestaurantPort;
    private final List<UpdateRestaurantPort> updateRestaurantPort;

    public MarkOrderReadyUseCaseImpl(LoadRestaurantPort loadRestaurantPort, List<UpdateRestaurantPort> updateRestaurantPort) {
        this.loadRestaurantPort = loadRestaurantPort;
        this.updateRestaurantPort = updateRestaurantPort;
    }


    @Override
    @Transactional
    public void markOrderReady(MarkOrderReadyCommand command) {
        Restaurant restaurant = loadRestaurantPort.loadById(command.restaurantId()).get();

        restaurant.markOrderReadyForPickUp(command.orderId());

        updateRestaurantPort.forEach(port -> port.updateRestaurant(restaurant));
    }
}
