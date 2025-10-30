package be.kdg.programming6.project.restaurantmanagement.port.in;

import be.kdg.programming6.project.restaurantmanagement.domain.Owner;

public interface SignupUseCase {
    Owner signup(SignupCommand command);
}
