package be.kdg.programming6.project.restaurantmanagement.port.in;

import be.kdg.programming6.project.restaurantmanagement.domain.valueobject.DishId;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ScheduleDraftsCommand(
        UUID restaurantId,
        List<DishId> dishIds,
        LocalDateTime publishTime
) {
}
