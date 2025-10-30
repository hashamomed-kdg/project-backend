package be.kdg.programming6.project.restaurantmanagement.core;

import be.kdg.programming6.project.restaurantmanagement.domain.Dish;
import be.kdg.programming6.project.restaurantmanagement.domain.Restaurant;
import be.kdg.programming6.project.restaurantmanagement.domain.valueobject.DishId;
import be.kdg.programming6.project.restaurantmanagement.domain.valueobject.RestaurantId;
import be.kdg.programming6.project.restaurantmanagement.port.in.EditDishCommand;
import be.kdg.programming6.project.restaurantmanagement.port.in.EditDishUseCase;
import be.kdg.programming6.project.restaurantmanagement.port.out.*;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EditDishUseCaseImpl implements EditDishUseCase {

    private final List<SaveDishPort> saveDishPort;
    private final UpdateDraftPort updateDraftPort;
    private final LoadRestaurantPort loadRestaurantPort;
    private final Logger log = LoggerFactory.getLogger(EditDishUseCaseImpl.class);

    public EditDishUseCaseImpl(List<SaveDishPort> saveDishPort, UpdateDraftPort updateDraftPort, LoadRestaurantPort loadRestaurantPort) {
        this.saveDishPort = saveDishPort;
        this.updateDraftPort = updateDraftPort;
        this.loadRestaurantPort = loadRestaurantPort;
    }

    @Override
    @Transactional
    public Dish editDish(EditDishCommand command) {
        Restaurant restaurant = loadRestaurantPort.loadById(RestaurantId.of(command.restaurantId())).get();
        List<Dish> dishes = restaurant.getMenu().getDishes();
        Dish draft = restaurant.getMenu().editDish(
                new Dish(
                        DishId.of(command.dishId()),
                        command.name(),
                        command.dishType(),
                        command.tags(),
                        command.description(),
                        command.price(),
                        command.pictureUrl()
                )
        );
        if (dishes.stream().anyMatch(dish -> dish.getParentId().get().equals(draft.getDishId()))) {
            updateDraftPort.updateDraft(draft);
        }

        saveDishPort.forEach(port -> port.createDraft(draft));
        return draft;
    }
}
