package be.kdg.programming6.project.restaurantmanagement.adapter.in.request;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ScheduleDraftsRequest(
        List<UUID> dishIds,
        LocalDateTime publishTime
) {
}
