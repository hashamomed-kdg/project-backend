package be.kdg.programming6.project.restaurantmanagement.core;

import be.kdg.programming6.project.restaurantmanagement.domain.Dish;
import be.kdg.programming6.project.restaurantmanagement.domain.Restaurant;
import be.kdg.programming6.project.restaurantmanagement.domain.valueobject.DishId;
import be.kdg.programming6.project.restaurantmanagement.domain.valueobject.RestaurantId;
import be.kdg.programming6.project.restaurantmanagement.port.in.ScheduleDraftsCommand;
import be.kdg.programming6.project.restaurantmanagement.port.in.ScheduleDraftsUseCase;
import be.kdg.programming6.project.restaurantmanagement.port.out.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ScheduleDraftsUseCaseImpl implements ScheduleDraftsUseCase {

    private final LoadRestaurantPort loadRestaurantPort;
    private final LoadDishPort loadDishPort;
    private final UpdateDraftPort updateDraftPort;

    public ScheduleDraftsUseCaseImpl(LoadRestaurantPort loadRestaurantPort, LoadDishPort loadDishPort, UpdateDraftPort updateDraftPort) {
        this.loadRestaurantPort = loadRestaurantPort;
        this.loadDishPort = loadDishPort;
        this.updateDraftPort = updateDraftPort;
    }


    @Override
    @Transactional
    public Restaurant scheduleDrafts(ScheduleDraftsCommand command) {
         Restaurant restaurant = loadRestaurantPort.loadById(RestaurantId.of(command.restaurantId())).get();
         for (DishId dish : command.dishIds()) {
             Dish draft = loadDishPort.loadById(DishId.of(dish.uuid())).get();
             draft.schedulePublication(command.publishTime());
             updateDraftPort.updateScheduled(draft);
         }
         return restaurant;
    }
}
