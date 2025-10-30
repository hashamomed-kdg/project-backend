package be.kdg.programming6.project.restaurantmanagement.core;

import be.kdg.programming6.project.restaurantmanagement.domain.Restaurant;
import be.kdg.programming6.project.restaurantmanagement.domain.valueobject.RestaurantId;
import be.kdg.programming6.project.restaurantmanagement.port.in.CreateRestaurantCommand;
import be.kdg.programming6.project.restaurantmanagement.port.in.CreateRestaurantUseCase;
import be.kdg.programming6.project.restaurantmanagement.port.out.SaveRestaurantPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreateRestaurantUseCaseImpl implements CreateRestaurantUseCase {

    private final List<SaveRestaurantPort> saveRestaurantPort;

    public CreateRestaurantUseCaseImpl(List<SaveRestaurantPort> saveRestaurantPort) {
        this.saveRestaurantPort = saveRestaurantPort;
    }

    @Override
    @Transactional
    public Restaurant createRestaurant(CreateRestaurantCommand command) {
         Restaurant restaurant = new Restaurant(
                RestaurantId.create(),
                command.owner(),
                command.restaurantName(),
                command.address(),
                command.emailAddress(),
                command.pictures(),
                command.cuisineType(),
                command.defaultPreparationTime(),
                command.openingHours()
         );
         saveRestaurantPort.forEach(port -> port.saveRestaurant(restaurant));
         return restaurant;
    }
}
