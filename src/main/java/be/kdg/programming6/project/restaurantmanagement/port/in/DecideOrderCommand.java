package be.kdg.programming6.project.restaurantmanagement.port.in;

import java.util.UUID;

public record DecideOrderCommand(
        UUID orderId,
        UUID restaurantId,
        boolean accepted,
        String reason,
        String street,
        String number,
        String postalCode,
        String city

) {
}
