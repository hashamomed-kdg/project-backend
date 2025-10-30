package be.kdg.programming6.project.common.events.restaurant;

import be.kdg.programming6.project.common.events.DomainEvent;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public record DishEditedEvent(
        LocalDateTime eventPit,
        UUID dish,
        String name,
        String dishType,
        Set<String> foodTags,
        String description,
        BigDecimal price,
        String pictureUrl,
        String state
) implements DomainEvent {

    public DishEditedEvent(UUID dish, String name, String dishType, Set<String> foodTags, String description, BigDecimal price, String pictureUrl, String state) {
        this(LocalDateTime.now(), dish, name, dishType, foodTags, description, price, pictureUrl, state);
    }
    @Override
    public LocalDateTime eventPit() {
        return eventPit;
    }
}
