package be.kdg.programming6.project.restaurantmanagement.core;

import be.kdg.programming6.project.restaurantmanagement.adapter.in.request.AuthResponse;
import be.kdg.programming6.project.restaurantmanagement.domain.Owner;
import be.kdg.programming6.project.restaurantmanagement.domain.Restaurant;
import be.kdg.programming6.project.restaurantmanagement.port.in.LoginCommand;
import be.kdg.programming6.project.restaurantmanagement.port.in.LoginUseCase;
import be.kdg.programming6.project.restaurantmanagement.port.out.LoadOwnerPort;
import be.kdg.programming6.project.restaurantmanagement.port.out.LoadRestaurantPort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginUseCaseImpl implements LoginUseCase {

    private final LoadOwnerPort loadOwnerPort;
    private final LoadRestaurantPort loadRestaurantPort;

    public LoginUseCaseImpl(LoadOwnerPort loadOwnerPort, LoadRestaurantPort loadRestaurantPort) {
        this.loadOwnerPort = loadOwnerPort;
        this.loadRestaurantPort = loadRestaurantPort;
    }


    @Override
    public AuthResponse login(LoginCommand command) {
        Owner owner = loadOwnerPort.loadOwnerByEmail(command.email().email())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        // Validate password
        if (!owner.getPassword().equals(command.password())) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        // Check if owner has a restaurant
        Optional<Restaurant> restaurant = loadRestaurantPort.loadByOwnerId(owner.getOwnerId());

        return new AuthResponse(
                owner.getOwnerId().uuid(),
                owner.getEmail().email(),
                owner.getFullName(),
                restaurant.map(Restaurant::getRestaurantId).map(id -> id.uuid()).orElse(null)
        );
    }
}
