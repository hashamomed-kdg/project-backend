package be.kdg.programming6.project.common.events.order;

import be.kdg.programming6.project.common.events.DomainEvent;

import java.time.LocalDateTime;
import java.util.UUID;

public record OrderRejectedEvent(LocalDateTime eventPit, UUID orderId, UUID restaurantId, String reason) implements DomainEvent {

    public OrderRejectedEvent(UUID orderId, UUID restaurantId, String reason) {
        this(LocalDateTime.now(), orderId, restaurantId, reason);
    }


    @Override
    public LocalDateTime eventPit() {
        return eventPit;
    }
}
