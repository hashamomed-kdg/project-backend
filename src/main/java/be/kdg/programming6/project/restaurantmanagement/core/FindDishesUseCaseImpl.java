package be.kdg.programming6.project.restaurantmanagement.core;

import be.kdg.programming6.project.restaurantmanagement.domain.Dish;
import be.kdg.programming6.project.restaurantmanagement.port.in.FindAllDishesByRestaurantCommand;
import be.kdg.programming6.project.restaurantmanagement.port.in.FindDishesUseCase;
import be.kdg.programming6.project.restaurantmanagement.port.out.LoadDishPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindDishesUseCaseImpl implements FindDishesUseCase {

    private final LoadDishPort loadDishPort;

    public FindDishesUseCaseImpl(LoadDishPort loadDishPort) {
        this.loadDishPort = loadDishPort;
    }


    @Override
    public List<Dish> findAllDishesByRestaurant(FindAllDishesByRestaurantCommand command) {
        List<Dish> dishes = loadDishPort.loadByRestaurantId(command.restaurantId().uuid());

        return dishes;
    }
}
