package be.kdg.programming6.project.restaurantmanagement.core;

import be.kdg.programming6.project.restaurantmanagement.domain.Dish;
import be.kdg.programming6.project.restaurantmanagement.domain.Menu;
import be.kdg.programming6.project.restaurantmanagement.domain.valueobject.DishId;
import be.kdg.programming6.project.restaurantmanagement.domain.valueobject.RestaurantId;
import be.kdg.programming6.project.restaurantmanagement.port.in.ChangeStateDishCommand;
import be.kdg.programming6.project.restaurantmanagement.port.in.ChangeStateDishUseCase;
import be.kdg.programming6.project.restaurantmanagement.port.out.LoadDishPort;
import be.kdg.programming6.project.restaurantmanagement.port.out.LoadRestaurantPort;
import be.kdg.programming6.project.restaurantmanagement.port.out.UpdateDishPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChangeStateDishUseCaseImpl implements ChangeStateDishUseCase {

    private final LoadDishPort loadDishPort;
    private final List<UpdateDishPort> updateDishPort;
    private final LoadRestaurantPort loadRestaurantPort;

    public ChangeStateDishUseCaseImpl(LoadDishPort loadDishPort, List<UpdateDishPort> updateDishPort, LoadRestaurantPort loadRestaurantPort) {
        this.loadDishPort = loadDishPort;
        this.updateDishPort = updateDishPort;
        this.loadRestaurantPort = loadRestaurantPort;
    }

    @Override
    @Transactional
    public Dish changeStateDish(ChangeStateDishCommand command) {
        Menu menu = loadRestaurantPort.loadById(RestaurantId.of(command.restaurantId())).get().getMenu();
        Dish dish = loadDishPort.loadById(DishId.of(command.dishId())).get();
        menu.changeDishState(dish, command.state());
        updateDishPort.forEach(port -> port.changeStateDish(dish));
        return dish;
    }
}
