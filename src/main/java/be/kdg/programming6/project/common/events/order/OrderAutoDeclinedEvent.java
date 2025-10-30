package be.kdg.programming6.project.common.events.order;

import be.kdg.programming6.project.common.events.DomainEvent;

import java.time.LocalDateTime;
import java.util.UUID;

public record OrderAutoDeclinedEvent(LocalDateTime eventPit, UUID orderId, UUID restaurantId) implements DomainEvent {

    public OrderAutoDeclinedEvent(UUID orderId, UUID restaurantId) {
        this(LocalDateTime.now(), orderId, restaurantId);
    }
    @Override
    public LocalDateTime eventPit() {
        return eventPit;
    }
}
