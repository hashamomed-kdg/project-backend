package be.kdg.programming6.project.ordermanagement.port.in;

import be.kdg.programming6.project.ordermanagement.domain.valueobject.OrderId;
import be.kdg.programming6.project.ordermanagement.domain.valueobject.RestaurantId;

public record OrderAcceptedCommand(
        OrderId orderId
) {
}
