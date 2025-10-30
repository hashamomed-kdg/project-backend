package be.kdg.programming6.project.restaurantmanagement.adapter.in.response;

import java.util.UUID;

public record OwnerDto(
        UUID ownerId, String email, String fullName
) {
}
