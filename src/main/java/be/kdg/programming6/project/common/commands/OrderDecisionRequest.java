package be.kdg.programming6.project.common.commands;

import be.kdg.programming6.project.common.events.order.AddressDtoForEvent;

import java.util.UUID;

public record OrderDecisionRequest(UUID orderId, UUID restaurantId, AddressDtoForEvent deliveryAddress) {
}
