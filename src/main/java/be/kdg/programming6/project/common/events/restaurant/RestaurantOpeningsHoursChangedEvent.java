package be.kdg.programming6.project.common.events.restaurant;

import be.kdg.programming6.project.common.events.DomainEvent;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public record RestaurantOpeningsHoursChangedEvent(LocalDateTime eventPit, UUID owner, UUID restaurant, Map<String, List<TimeRangeDtoForEvent>> openingHours) implements DomainEvent {

    public RestaurantOpeningsHoursChangedEvent(UUID owner, UUID restaurant, Map<String, List<TimeRangeDtoForEvent>> openingHours) {
        this(LocalDateTime.now(), owner, restaurant, openingHours);
    }

    @Override
    public LocalDateTime eventPit() {
        return eventPit;
    }


}
