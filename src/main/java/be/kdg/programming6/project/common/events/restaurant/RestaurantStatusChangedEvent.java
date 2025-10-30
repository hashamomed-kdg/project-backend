package be.kdg.programming6.project.common.events.restaurant;

import be.kdg.programming6.project.common.events.DomainEvent;

import java.time.LocalDateTime;
import java.util.UUID;

public record RestaurantStatusChangedEvent(LocalDateTime eventPit, UUID owner, UUID restaurant, String status ) implements DomainEvent {

    public RestaurantStatusChangedEvent(UUID owner, UUID restaurant, String status) {
        this(LocalDateTime.now(), owner, restaurant, status);
    }


    @Override
    public LocalDateTime eventPit() {
        return eventPit;
    }
}
