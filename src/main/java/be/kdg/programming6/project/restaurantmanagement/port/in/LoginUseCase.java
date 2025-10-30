package be.kdg.programming6.project.restaurantmanagement.port.in;

import be.kdg.programming6.project.restaurantmanagement.adapter.in.request.AuthResponse;

public interface LoginUseCase {
    AuthResponse login(LoginCommand command);
}
