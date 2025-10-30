package be.kdg.programming6.project.restaurantmanagement.adapter.in.request;

import java.util.UUID;

public record AuthResponse(
        UUID ownerId,
        String email,
        String fullName,
        UUID restaurantId // null if no restaurant
) {
}
