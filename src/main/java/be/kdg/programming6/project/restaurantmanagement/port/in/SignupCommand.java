package be.kdg.programming6.project.restaurantmanagement.port.in;

import be.kdg.programming6.project.restaurantmanagement.domain.valueobject.EmailAddress;

public record SignupCommand(
        EmailAddress email, String password, String fullName
) {
}
