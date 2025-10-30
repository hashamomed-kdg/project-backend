package be.kdg.programming6.project.ordermanagement.adapter.in.request;

import java.math.BigDecimal;
import java.util.UUID;

public record AddItemInBasketRequest(
        UUID basketId,
        UUID dishId,
        UUID restaurantId,
        String name,
        BigDecimal price,
        long quantity
) {
}
