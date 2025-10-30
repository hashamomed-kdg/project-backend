package be.kdg.programming6.project.ordermanagement.port.out;

import be.kdg.programming6.project.ordermanagement.domain.Order;

public interface SaveOrderPort {

    Order saveOrder(Order order);
}
