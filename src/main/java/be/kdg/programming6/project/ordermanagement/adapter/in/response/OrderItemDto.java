package be.kdg.programming6.project.ordermanagement.adapter.in.response;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderItemDto(
        UUID dishId,
        String name,
        BigDecimal price,
        long quantity
) {
}
