package be.kdg.programming6.project.common.events.order;

import be.kdg.programming6.project.common.events.DomainEvent;

import java.time.LocalDateTime;
import java.util.UUID;

public record OrderSubmittedEvent(
        LocalDateTime eventPit,
        UUID orderId,
        UUID restaurantId,
        AddressDtoForEvent deliveryAddress) implements DomainEvent {


    public OrderSubmittedEvent(UUID orderId, UUID restaurantId, AddressDtoForEvent deliveryAddress) {
        this(LocalDateTime.now(), orderId, restaurantId, deliveryAddress );
    }

    @Override
    public LocalDateTime eventPit() {
        return eventPit;
    }

}
