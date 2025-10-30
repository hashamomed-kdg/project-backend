package be.kdg.programming6.project.common.events.order;

import be.kdg.programming6.project.common.events.DomainEvent;
import org.springframework.modulith.events.Externalized;

import java.time.LocalDateTime;
import java.util.UUID;


@Externalized("kdg.events::#{'restaurant.' + #this.restaurantId() + '.order.ready.v1'}")
public record OrderReadyForPickupEvent(
        UUID eventId,
        LocalDateTime occurredAt,
        UUID orderId,
        UUID restaurantId
) implements DomainEvent
{
    public OrderReadyForPickupEvent(UUID orderId, UUID restaurantId) {
        this(UUID.randomUUID(), LocalDateTime.now(), orderId, restaurantId);
    }


    @Override
    public LocalDateTime eventPit() {
        return occurredAt;
    }
}
