package be.kdg.programming6.project.ordermanagement.domain;

import be.kdg.programming6.project.common.events.DomainEvent;
import be.kdg.programming6.project.common.events.order.AddressDtoForEvent;
import be.kdg.programming6.project.common.events.order.OrderAutoDeclinedEvent;
import be.kdg.programming6.project.common.events.order.OrderSubmittedEvent;
import be.kdg.programming6.project.ordermanagement.domain.valueobject.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Order {

    private OrderId orderId;
    private RestaurantId restaurantId;
    private CustomerInfo customerInfo;
    private List<OrderItem> orderItems;
    private BigDecimal totalPrice;
    private OrderStatus orderStatus;
    private String rejectionReason;

    private final List<DomainEvent> domainEvents = new ArrayList<>();

    public Order(OrderId orderId, RestaurantId restaurantId, CustomerInfo customerInfo, List<OrderItem> orderItems, BigDecimal totalPrice) {
        this.orderId = orderId;
        this.restaurantId = restaurantId;
        this.customerInfo = customerInfo;
        this.orderItems = orderItems;
        this.totalPrice = totalPrice;
        this.orderStatus = OrderStatus.SUBMITTED;

        domainEvents.add(new OrderSubmittedEvent(
                orderId.uuid(),
                restaurantId.uuid(),
                new AddressDtoForEvent(
                        customerInfo.deliveryAddress().street(),
                        customerInfo.deliveryAddress().number(),
                        customerInfo.deliveryAddress().postalCode(),
                        customerInfo.deliveryAddress().city()
                        )));
    }

    private BigDecimal calculateTotalPrice() {
        return orderItems.stream()
                .map(OrderItem::getSubTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void acceptOrder() {
        if (isSubmitted())
            throw new IllegalStateException("Order cannot be confirmed");
        this.orderStatus = OrderStatus.ACCEPTED;
    }

    public void autoDeclineOrder() {
        if (isSubmitted())
            throw new IllegalStateException("Order cannot be declined");
        this.orderStatus = OrderStatus.AUTO_DECLINED;
        domainEvents.add(new OrderAutoDeclinedEvent(orderId.uuid(), restaurantId.uuid()));
    }

    public void rejectOrder(String reason) {
        if (isSubmitted())
            throw new IllegalStateException("Order cannot be rejected");
        this.rejectionReason = reason;
        this.orderStatus = OrderStatus.REJECTED;
    }

    public void markAsReadyForPickup() {
        if (orderStatus != OrderStatus.ACCEPTED)
            throw new IllegalStateException("Order cannot be marked as ready for pickup");
        this.orderStatus = OrderStatus.READY_FOR_PICKUP;
    }

    public void markAsPickedUp() {
        if (orderStatus != OrderStatus.READY_FOR_PICKUP)
            throw new IllegalStateException("Order cannot be marked as picked up");
        this.orderStatus = OrderStatus.PICKED_UP;
    }

    public void markAsDelivered() {
        if (orderStatus != OrderStatus.PICKED_UP)
            throw new IllegalStateException("Order cannot be marked as delivered");
        this.orderStatus = OrderStatus.DELIVERED;
    }

    public boolean isSubmitted() {
        return orderStatus == OrderStatus.SUBMITTED;
    }



    public OrderId getOrderId() {
        return orderId;
    }

    public RestaurantId getRestaurantId() {
        return restaurantId;
    }

    public CustomerInfo getCustomerInfo() {
        return customerInfo;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public List<DomainEvent> getDomainEvents() {
        return domainEvents;
    }
}
