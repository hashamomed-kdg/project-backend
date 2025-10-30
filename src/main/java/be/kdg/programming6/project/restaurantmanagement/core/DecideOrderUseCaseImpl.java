package be.kdg.programming6.project.restaurantmanagement.core;

import be.kdg.programming6.project.common.commands.OrderDecisionRequest;
import be.kdg.programming6.project.common.events.order.AddressDtoForEvent;
import be.kdg.programming6.project.restaurantmanagement.domain.Restaurant;
import be.kdg.programming6.project.restaurantmanagement.domain.valueobject.RestaurantId;
import be.kdg.programming6.project.restaurantmanagement.port.in.DecideOrderCommand;
import be.kdg.programming6.project.restaurantmanagement.port.in.DecideOrderUseCase;
import be.kdg.programming6.project.restaurantmanagement.port.out.LoadRestaurantPort;
import be.kdg.programming6.project.restaurantmanagement.port.out.SaveRestaurantPort;
import be.kdg.programming6.project.restaurantmanagement.port.out.UpdateRestaurantPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DecideOrderUseCaseImpl implements DecideOrderUseCase {

    private final LoadRestaurantPort loadRestaurantPort;
    private final List<UpdateRestaurantPort> updateRestaurantPort;

    public DecideOrderUseCaseImpl(LoadRestaurantPort loadRestaurantPort, List<UpdateRestaurantPort> updateRestaurantPort) {
        this.loadRestaurantPort = loadRestaurantPort;
        this.updateRestaurantPort = updateRestaurantPort;
    }

    @Override
    public Restaurant decideOrder(DecideOrderCommand command) {
        Restaurant restaurant = loadRestaurantPort.loadById(RestaurantId.of(command.restaurantId())).get();

        OrderDecisionRequest request = new OrderDecisionRequest(
                command.orderId(),
                command.restaurantId(),
                new AddressDtoForEvent(
                        command.street(),
                        command.number(),
                        command.postalCode(),
                        command.city()
                )
        );

        if (command.accepted()) {
            restaurant.acceptOrder(request);
        } else {
            restaurant.rejectOrder(request, command.reason());
        }

        updateRestaurantPort.forEach(u -> u.updateRestaurant(restaurant)); // to publish events
        return restaurant;
    }
}
