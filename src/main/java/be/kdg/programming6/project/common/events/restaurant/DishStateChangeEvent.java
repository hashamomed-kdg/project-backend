package be.kdg.programming6.project.common.events.restaurant;


import be.kdg.programming6.project.common.events.DomainEvent;

import java.time.LocalDateTime;
import java.util.UUID;

public record DishStateChangeEvent(LocalDateTime eventPit, UUID dish, String state) implements DomainEvent {

    public DishStateChangeEvent(UUID dish, String state) {
        this(LocalDateTime.now(), dish, state);
    }

    @Override
    public LocalDateTime eventPit() {
        return eventPit;
    }
}
