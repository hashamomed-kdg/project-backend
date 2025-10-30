package be.kdg.programming6.project.ordermanagement.domain.valueobject;

import java.util.UUID;

public record OrderId (UUID uuid){
    public static OrderId create() {
        return new OrderId(UUID.randomUUID());
    }

    public static OrderId of(UUID orderId) {
        return new OrderId(orderId);
    }
}
