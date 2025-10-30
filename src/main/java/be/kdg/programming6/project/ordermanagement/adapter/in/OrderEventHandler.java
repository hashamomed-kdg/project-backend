package be.kdg.programming6.project.ordermanagement.adapter.in;

import be.kdg.programming6.project.common.config.RabbitMQTopology;
import be.kdg.programming6.project.common.events.order.*;
import be.kdg.programming6.project.ordermanagement.domain.valueobject.OrderId;
import be.kdg.programming6.project.ordermanagement.domain.valueobject.RestaurantId;
import be.kdg.programming6.project.ordermanagement.port.in.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class OrderEventHandler {

    private final RequestOrderDecisionUseCase requestOrderDecisionUseCase;
    private final HandleOrderStatusChangeUseCase handleOrderStatusChangeUseCase;
    private final Logger log = LoggerFactory.getLogger(OrderEventHandler.class);

    public OrderEventHandler(RequestOrderDecisionUseCase requestOrderDecisionUseCase, HandleOrderStatusChangeUseCase handleOrderStatusChangeUseCase) {
        this.requestOrderDecisionUseCase = requestOrderDecisionUseCase;
        this.handleOrderStatusChangeUseCase = handleOrderStatusChangeUseCase;
    }

    @EventListener
    public void handle(OrderSubmittedEvent event){
        RequestOrderDecisionCommand command = new RequestOrderDecisionCommand(
                OrderId.of(event.orderId()),
                RestaurantId.of(event.restaurantId()),
                event.deliveryAddress().street(),
                event.deliveryAddress().number(),
                event.deliveryAddress().postalCode(),
                event.deliveryAddress().city());

        requestOrderDecisionUseCase.requestDecision(command);
    }

    @EventListener
    public void handle(OrderAcceptedEvent event){
        OrderAcceptedCommand command = new OrderAcceptedCommand(
                OrderId.of(event.orderId()));

        handleOrderStatusChangeUseCase.handleAcceptedOrder(command);
    }

    @EventListener
    public void handle(OrderRejectedEvent event){
        OrderRejectedCommand command = new OrderRejectedCommand(
                OrderId.of(event.orderId()),
                event.reason());

        handleOrderStatusChangeUseCase.handleRejectedOrder(command);
    }

    @EventListener
    public void handle(OrderReadyForPickupEvent event){
        HandleOrderStatusChangeCommand command = new HandleOrderStatusChangeCommand(
                OrderId.of(event.orderId())
        );

        handleOrderStatusChangeUseCase.handleOrderMarkedReady(command);
    }

    @RabbitListener(queues = RabbitMQTopology.ORDER_PICKED_UP_QUEUE)
    public void handle(OrderPickedUpEvent event){
        HandleOrderStatusChangeCommand command = new HandleOrderStatusChangeCommand(
                OrderId.of(event.orderId())
        );

        handleOrderStatusChangeUseCase.handleOrderPickedUp(command);
        log.info("Order {} picked up", event.orderId());
    }

    @RabbitListener(queues = RabbitMQTopology.ORDER_DELIVERED_QUEUE)
    public void handle(OrderDeliveredEvent event){
        HandleOrderStatusChangeCommand command = new HandleOrderStatusChangeCommand(
                OrderId.of(event.orderId())
        );

        handleOrderStatusChangeUseCase.handleOrderDelivered(command);
        log.info("Order {} delivered", event.orderId());
    }


}
