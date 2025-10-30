package be.kdg.programming6.project.ordermanagement.core;

import be.kdg.programming6.project.ordermanagement.domain.Order;
import be.kdg.programming6.project.ordermanagement.port.in.HandleOrderStatusChangeCommand;
import be.kdg.programming6.project.ordermanagement.port.in.HandleOrderStatusChangeUseCase;
import be.kdg.programming6.project.ordermanagement.port.in.OrderAcceptedCommand;
import be.kdg.programming6.project.ordermanagement.port.in.OrderRejectedCommand;
import be.kdg.programming6.project.ordermanagement.port.out.LoadOrderPort;
import be.kdg.programming6.project.ordermanagement.port.out.SaveOrderPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HandleOrderStatusChangeUseCaseImpl implements HandleOrderStatusChangeUseCase {

    private final LoadOrderPort loadOrderPort;
    private final List<SaveOrderPort> saveOrderPort;

    public HandleOrderStatusChangeUseCaseImpl(LoadOrderPort loadOrderPort, List<SaveOrderPort> saveOrderPort) {
        this.loadOrderPort = loadOrderPort;
        this.saveOrderPort = saveOrderPort;
    }

    @Override
    @Transactional
    public Order handleAcceptedOrder(OrderAcceptedCommand command) {
        Order order = loadOrderPort.loadOrderById(command.orderId()).get();
        order.acceptOrder();
        saveOrderPort.forEach(port -> port.saveOrder(order));
        return order;
    }

    @Override
    @Transactional
    public Order handleRejectedOrder(OrderRejectedCommand command) {
        Order order = loadOrderPort.loadOrderById(command.orderId()).get();
        order.rejectOrder(command.reason());
        saveOrderPort.forEach(port -> port.saveOrder(order));
        return order;
    }

    @Override
    @Transactional
    public Order handleOrderMarkedReady(HandleOrderStatusChangeCommand command) {
        Order order = loadOrderPort.loadOrderById(command.orderId()).get();
        order.markAsReadyForPickup();
        saveOrderPort.forEach(port -> port.saveOrder(order));
        return order;
    }

    @Override
    @Transactional
    public Order handleOrderPickedUp(HandleOrderStatusChangeCommand command) {
        Order order = loadOrderPort.loadOrderById(command.orderId()).get();
        order.markAsPickedUp();
        saveOrderPort.forEach(port -> port.saveOrder(order));
        return order;
    }

    @Override
    @Transactional
    public Order handleOrderDelivered(HandleOrderStatusChangeCommand command) {
        Order order = loadOrderPort.loadOrderById(command.orderId()).get();
        order.markAsDelivered();
        saveOrderPort.forEach(port -> port.saveOrder(order));
        return order;
    }

}
