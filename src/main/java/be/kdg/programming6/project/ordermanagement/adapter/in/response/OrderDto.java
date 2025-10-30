package be.kdg.programming6.project.ordermanagement.adapter.in.response;

import be.kdg.programming6.project.ordermanagement.adapter.in.request.CustomerInfoDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record OrderDto(
        UUID orderId,
        UUID restaurantId,
        CustomerInfoDto customerInfo,
        List<OrderItemDto> orderItems,
        BigDecimal totalPrice,
        String orderStatus

) {
}
