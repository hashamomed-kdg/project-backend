package be.kdg.programming6.project.ordermanagement.adapter.in.response;

import java.math.BigDecimal;
import java.util.UUID;

public record BasketItemDto (
        UUID dishId,
        UUID restaurantId,
        String name,
        BigDecimal price,
        long quantity
){
}
