package be.kdg.programming6.project.ordermanagement.port.in;

import be.kdg.programming6.project.ordermanagement.domain.Order;

public interface HandleOrderStatusChangeUseCase {

    Order handleAcceptedOrder(OrderAcceptedCommand command);
    Order handleRejectedOrder(OrderRejectedCommand command);
    Order handleOrderMarkedReady(HandleOrderStatusChangeCommand command);
    Order handleOrderPickedUp(HandleOrderStatusChangeCommand command);
    Order handleOrderDelivered(HandleOrderStatusChangeCommand command);
}
