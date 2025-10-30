package be.kdg.programming6.project.restaurantmanagement.port.in;

import be.kdg.programming6.project.restaurantmanagement.domain.valueobject.EmailAddress;

public record LoginCommand(
        EmailAddress email, String password
) {
}
