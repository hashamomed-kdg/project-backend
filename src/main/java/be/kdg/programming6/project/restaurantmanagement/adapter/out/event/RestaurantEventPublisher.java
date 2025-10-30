package be.kdg.programming6.project.restaurantmanagement.adapter.out.event;

import be.kdg.programming6.project.restaurantmanagement.domain.Dish;
import be.kdg.programming6.project.restaurantmanagement.domain.Restaurant;
import be.kdg.programming6.project.restaurantmanagement.domain.valueobject.RestaurantId;
import be.kdg.programming6.project.restaurantmanagement.port.out.*;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class RestaurantEventPublisher implements UpdateRestaurantPort, UpdateDishPort, SaveRestaurantPort, SaveDishPort {

    private final ApplicationEventPublisher eventPublisher;

    public RestaurantEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Restaurant updateRestaurant(Restaurant restaurant) {
        restaurant.getDomainEvents().forEach(eventPublisher::publishEvent);
        return restaurant;
    }

    @Override
    public Dish changeStateDish(Dish dish) {
        dish.getDomainEvents().forEach(eventPublisher::publishEvent);
        return dish;
    }

    @Override
    public Dish updateDish(RestaurantId restaurantId, Dish dish) {
        dish.getDomainEvents().forEach(eventPublisher::publishEvent);
        return dish;
    }


    @Override
    public Dish saveDish(RestaurantId restaurantId, Dish dish) {
        dish.getDomainEvents().forEach(eventPublisher::publishEvent);
        return dish;
    }

    @Override
    public Restaurant saveRestaurant(Restaurant restaurant) {
        restaurant.getDomainEvents().forEach(eventPublisher::publishEvent);
        return restaurant;
    }

    @Override
    public Dish createDraft(Dish dish) { //not using can be separated to a new port
        return null;
    }
}
