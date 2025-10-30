package be.kdg.programming6.project.ordermanagement.adapter.out.event;

import be.kdg.programming6.project.ordermanagement.domain.Order;
import be.kdg.programming6.project.ordermanagement.port.out.SaveOrderPort;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class OrderEventPublisher implements SaveOrderPort {

    private final ApplicationEventPublisher eventPublisher;

    public OrderEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Order saveOrder(Order order) {
        order.getDomainEvents().forEach(eventPublisher::publishEvent);
        return order;
    }
}
