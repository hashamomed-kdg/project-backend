package be.kdg.programming6.project.common.events.order;

import be.kdg.programming6.project.common.events.DomainEvent;

import java.time.LocalDateTime;
import java.util.UUID;

public record OrderDeliveredEvent(
        UUID eventId,
        LocalDateTime occurredAt,
        UUID orderId,
        UUID restaurantId
) implements DomainEvent
{
    public OrderDeliveredEvent(UUID orderId, UUID restaurantId) {
        this(UUID.randomUUID(), LocalDateTime.now(), orderId, restaurantId);
    }

    @Override
    public LocalDateTime eventPit() {
        return occurredAt;
    }
}
