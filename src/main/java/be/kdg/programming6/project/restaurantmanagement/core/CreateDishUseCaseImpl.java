package be.kdg.programming6.project.restaurantmanagement.core;

import be.kdg.programming6.project.restaurantmanagement.domain.Dish;
import be.kdg.programming6.project.restaurantmanagement.domain.Restaurant;
import be.kdg.programming6.project.restaurantmanagement.domain.valueobject.DishId;
import be.kdg.programming6.project.restaurantmanagement.domain.valueobject.RestaurantId;
import be.kdg.programming6.project.restaurantmanagement.port.in.CreateDishCommand;
import be.kdg.programming6.project.restaurantmanagement.port.in.CreateDishUseCase;
import be.kdg.programming6.project.restaurantmanagement.port.out.LoadRestaurantPort;
import be.kdg.programming6.project.restaurantmanagement.port.out.SaveDishPort;
import be.kdg.programming6.project.restaurantmanagement.port.out.SaveRestaurantPort;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreateDishUseCaseImpl implements CreateDishUseCase {

    private final List<SaveDishPort> saveDishPort;
    private final LoadRestaurantPort loadRestaurantPort;
    private final Logger log = LoggerFactory.getLogger(CreateDishUseCaseImpl.class);


    public CreateDishUseCaseImpl(List<SaveDishPort> saveDishPort, LoadRestaurantPort loadRestaurantPort) {
        this.saveDishPort = saveDishPort;
        this.loadRestaurantPort = loadRestaurantPort;
    }

    @Override
    @Transactional
    public Dish createDish(CreateDishCommand command) {
        Restaurant restaurant = loadRestaurantPort.loadById(RestaurantId.of(command.restaurantId())).get();
        Dish dish = new Dish(
                DishId.create(),
                command.name(),
                command.dishType(),
                command.tags(),
                command.description(),
                command.price(),
                command.pictureUrl(),
                command.state()
        );
        restaurant.getMenu().addDish(dish);
        saveDishPort.forEach(port -> port.saveDish(RestaurantId.of(command.restaurantId()), dish));
        return dish;
    }
}
