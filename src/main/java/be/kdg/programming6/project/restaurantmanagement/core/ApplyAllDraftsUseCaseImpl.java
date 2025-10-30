package be.kdg.programming6.project.restaurantmanagement.core;

import be.kdg.programming6.project.restaurantmanagement.domain.Dish;
import be.kdg.programming6.project.restaurantmanagement.domain.Restaurant;
import be.kdg.programming6.project.restaurantmanagement.domain.valueobject.RestaurantId;
import be.kdg.programming6.project.restaurantmanagement.port.in.ApplyAllDraftsCommand;
import be.kdg.programming6.project.restaurantmanagement.port.in.ApplyAllDraftsUseCase;
import be.kdg.programming6.project.restaurantmanagement.port.out.*;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplyAllDraftsUseCaseImpl implements ApplyAllDraftsUseCase {

    private final LoadRestaurantPort loadRestaurantPort;
    private final List<SaveRestaurantPort> saveRestaurantPort;
    private final List<SaveDishPort> saveDishPort;
    private final Logger log = LoggerFactory.getLogger(ApplyAllDraftsUseCaseImpl.class);



    public ApplyAllDraftsUseCaseImpl(LoadRestaurantPort loadRestaurantPort, List<SaveRestaurantPort> saveRestaurantPort, List<SaveDishPort> saveDishPort) {
        this.loadRestaurantPort = loadRestaurantPort;
        this.saveRestaurantPort = saveRestaurantPort;
        this.saveDishPort = saveDishPort;
    }

    @Override
    @Transactional
    public Restaurant applyAllDrafts(ApplyAllDraftsCommand command) {
        Restaurant restaurant = loadRestaurantPort.loadById(RestaurantId.of(command.restaurantId())).get();
        restaurant.getMenu().applyAllDrafts();

        saveRestaurantPort.forEach(port -> port.saveRestaurant(restaurant));
        for (Dish dish : restaurant.getMenu().getDishes()) { // needed otherwise null value Restaurant in Dish
            saveDishPort.forEach(port -> port.saveDish(RestaurantId.of(command.restaurantId()), dish));
        }

        return restaurant;
    }
}
