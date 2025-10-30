package be.kdg.programming6.project.ordermanagement.core;

import be.kdg.programming6.project.common.commands.OrderDecisionRequest;
import be.kdg.programming6.project.common.events.order.AddressDtoForEvent;
import be.kdg.programming6.project.ordermanagement.domain.Order;
import be.kdg.programming6.project.ordermanagement.domain.valueobject.OrderId;
import be.kdg.programming6.project.ordermanagement.port.in.RequestOrderDecisionCommand;
import be.kdg.programming6.project.ordermanagement.port.in.RequestOrderDecisionUseCase;
import be.kdg.programming6.project.ordermanagement.port.out.CallRestaurantRestPort;
import be.kdg.programming6.project.ordermanagement.port.out.LoadOrderPort;
import be.kdg.programming6.project.ordermanagement.port.out.SaveOrderPort;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class RequestOrderDecisionUseCaseImpl implements RequestOrderDecisionUseCase {

    private final CallRestaurantRestPort callRestaurantRestPort;
    private final LoadOrderPort loadOrderPort;
    private final List<SaveOrderPort> saveOrderPort;
    private final TaskScheduler taskScheduler;

    public RequestOrderDecisionUseCaseImpl(CallRestaurantRestPort callRestaurantRestPort, LoadOrderPort loadOrderPort, List<SaveOrderPort> saveOrderPort, TaskScheduler taskScheduler) {
        this.callRestaurantRestPort = callRestaurantRestPort;
        this.loadOrderPort = loadOrderPort;
        this.saveOrderPort = saveOrderPort;
        this.taskScheduler = taskScheduler;
    }

    @Override
    @Transactional
    public Order requestDecision(RequestOrderDecisionCommand command) {
        Order order = loadOrderPort.loadOrderById(command.orderId()).get();

        callRestaurantRestPort.requestForOrderDecision(new OrderDecisionRequest(
                command.orderId().uuid(),
                command.restaurantId().uuid(),
                new AddressDtoForEvent(
                        command.street(),
                        command.number(),
                        command.postalCode(),
                        command.city()
                )
        ));

        taskScheduler.schedule( () -> autoDeclineOrder(command.orderId()),
                Instant.now().plus(5, ChronoUnit.MINUTES)); // auto decline order after 5 minutes

        return order;
    }

    private void autoDeclineOrder(OrderId orderId) {
        Order order = loadOrderPort.loadOrderById(orderId).get();
        if (order.isSubmitted()) { // still pending for decision
            order.autoDeclineOrder();
            saveOrderPort.forEach(port -> port.saveOrder(order));
        }
    }
}
