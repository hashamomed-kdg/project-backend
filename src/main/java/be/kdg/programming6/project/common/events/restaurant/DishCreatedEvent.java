package be.kdg.programming6.project.common.events.restaurant;

import be.kdg.programming6.project.common.events.DomainEvent;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record DishCreatedEvent(
        LocalDateTime eventPit, UUID dishId, BigDecimal price, String state
) implements DomainEvent {

    public DishCreatedEvent(UUID dishId, BigDecimal price, String state) {
        this(LocalDateTime.now(), dishId, price, state);
    }

    @Override
    public LocalDateTime eventPit() {
        return eventPit;
    }
}
