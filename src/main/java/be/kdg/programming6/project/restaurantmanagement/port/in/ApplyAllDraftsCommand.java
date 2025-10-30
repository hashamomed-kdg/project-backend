package be.kdg.programming6.project.restaurantmanagement.port.in;

import java.util.UUID;

public record ApplyAllDraftsCommand(
        UUID restaurantId
) {
}
