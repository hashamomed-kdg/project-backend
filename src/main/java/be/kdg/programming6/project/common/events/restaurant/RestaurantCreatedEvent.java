package be.kdg.programming6.project.common.events.restaurant;

import be.kdg.programming6.project.common.events.DomainEvent;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public record RestaurantCreatedEvent(
        LocalDateTime eventPit,
        UUID owner,
        UUID restaurant,
        Map<String, List<TimeRangeDtoForEvent>> openingHours,
        String status
) implements DomainEvent {

    public RestaurantCreatedEvent(UUID owner, UUID restaurant, Map<String, List<TimeRangeDtoForEvent>> openingHours, String status) {
        this(LocalDateTime.now(), owner, restaurant, openingHours, status);
    }

    @Override
    public LocalDateTime eventPit() {
        return eventPit;
    }


}
