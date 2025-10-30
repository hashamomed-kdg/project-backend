package be.kdg.programming6.project.ordermanagement.adapter.in.response;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record BasketDto(
        UUID basketId,
        UUID restaurantId,
        List<BasketItemDto> basketItems,
        String basketStatus
) {
}
