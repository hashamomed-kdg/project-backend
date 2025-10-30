package be.kdg.programming6.project.ordermanagement.port.out;

import be.kdg.programming6.project.ordermanagement.domain.Order;
import be.kdg.programming6.project.ordermanagement.domain.valueobject.OrderId;

import java.util.Optional;

public interface LoadOrderPort {

    Optional<Order> loadOrderById(OrderId orderId);
}
