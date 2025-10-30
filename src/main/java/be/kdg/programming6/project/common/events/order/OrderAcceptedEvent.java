package be.kdg.programming6.project.common.events.order;

import be.kdg.programming6.project.common.events.DomainEvent;
import org.springframework.modulith.events.Externalized;

import java.time.LocalDateTime;
import java.util.UUID;

@Externalized("kdg.events::#{'restaurant.' + #this.restaurantId() + '.order.accepted.v1'}")
public record OrderAcceptedEvent(UUID eventId,
                                 LocalDateTime occurredAt,
                                 UUID orderId,
                                 UUID restaurantId,
                                 AddressDtoForEvent pickupAddress,
                                 RandomCoordinates pickUpRandomCoordinates,
                                 AddressDtoForEvent dropoffAddress,
                                 RandomCoordinates dropoffRandomCoordinates) implements DomainEvent {

    public OrderAcceptedEvent(UUID orderId, UUID restaurantId, AddressDtoForEvent pickupAdress, AddressDtoForEvent dropoffAddress) {
        this(UUID.randomUUID(), LocalDateTime.now(), orderId, restaurantId, pickupAdress, new RandomCoordinates(), dropoffAddress, new RandomCoordinates());
    }

    @Override
    public LocalDateTime eventPit() {
        return occurredAt;
    }
}
