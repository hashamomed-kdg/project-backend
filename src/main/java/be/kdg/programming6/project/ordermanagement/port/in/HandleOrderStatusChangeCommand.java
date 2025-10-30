package be.kdg.programming6.project.ordermanagement.port.in;

import be.kdg.programming6.project.ordermanagement.domain.valueobject.OrderId;

public record HandleOrderStatusChangeCommand(
        OrderId orderId
) {
}
