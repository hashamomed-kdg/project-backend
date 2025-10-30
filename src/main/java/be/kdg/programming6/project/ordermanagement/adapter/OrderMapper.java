package be.kdg.programming6.project.ordermanagement.adapter;

import be.kdg.programming6.project.ordermanagement.adapter.in.request.CustomerInfoDto;
import be.kdg.programming6.project.ordermanagement.adapter.in.response.OrderDto;
import be.kdg.programming6.project.ordermanagement.adapter.in.response.OrderItemDto;
import be.kdg.programming6.project.ordermanagement.adapter.out.jpa.entity.CustomerInfoEmbeddable;
import be.kdg.programming6.project.ordermanagement.adapter.out.jpa.entity.OrderItemJpaEntity;
import be.kdg.programming6.project.ordermanagement.adapter.out.jpa.entity.OrderJpaEntity;
import be.kdg.programming6.project.ordermanagement.domain.Order;
import be.kdg.programming6.project.ordermanagement.domain.valueobject.*;

public class OrderMapper {

    public static OrderJpaEntity toEntity(Order order) {
        OrderJpaEntity entity = new OrderJpaEntity(
                order.getOrderId().uuid(),
                order.getRestaurantId().uuid(),
                toCustomerInfoEmbeddable(order.getCustomerInfo()),
                order.getTotalPrice(),
                order.getOrderStatus()
        );
        entity.setOrderItems(order.getOrderItems().stream()
                .map(Item -> toOrderItemEntity(Item, entity)).toList());
        return entity;
    }

    public static Order toDomain(OrderJpaEntity entity){
        Order order = new Order(
                OrderId.of(entity.getId()),
                RestaurantId.of(entity.getRestaurantId()),
                toCustomerInfo(entity.getCustomerInfo()),
                entity.getOrderItems().stream()
                        .map(OrderMapper::toOrderItem).toList(),
                entity.getTotalPrice()

        );
        return order;
    }

    public static OrderDto toOrderDto(Order order) {
        return new OrderDto(
                order.getOrderId().uuid(),
                order.getRestaurantId().uuid(),
                new CustomerInfoDto(
                        order.getCustomerInfo().name(),
                        order.getCustomerInfo().emailAddress().email(),
                        order.getCustomerInfo().deliveryAddress().street(),
                        order.getCustomerInfo().deliveryAddress().number(),
                        order.getCustomerInfo().deliveryAddress().postalCode(),
                        order.getCustomerInfo().deliveryAddress().city(),
                        order.getCustomerInfo().deliveryAddress().country()
                ),
                order.getOrderItems().stream()
                        .map(
                                item -> new OrderItemDto(
                                        item.dishId().uuid(),
                                        item.name(),
                                        item.price(),
                                        item.quantity()
                                )
                        ).toList(),
                order.getTotalPrice(),
                order.getOrderStatus().name()
        );
    }

    public static CustomerInfo toCustomerInfo(CustomerInfoDto customerInfoDto) {
        return new CustomerInfo(
                customerInfoDto.name(),
                new DeliveryAddress(
                        customerInfoDto.street(),
                        customerInfoDto.number(),
                        customerInfoDto.postalCode(),
                        customerInfoDto.city(),
                        customerInfoDto.country()),
                EmailAddress.of(customerInfoDto.email())
        );
    }

    private static CustomerInfo toCustomerInfo(CustomerInfoEmbeddable customerInfoEmbeddable) {
        return new CustomerInfo(
                customerInfoEmbeddable.name(),
                new DeliveryAddress(
                        customerInfoEmbeddable.street(),
                        customerInfoEmbeddable.number(),
                        customerInfoEmbeddable.postalCode(),
                        customerInfoEmbeddable.city(),
                        customerInfoEmbeddable.country()),
                EmailAddress.of(customerInfoEmbeddable.emailAddress())
        );
    }

    private static CustomerInfoEmbeddable toCustomerInfoEmbeddable(CustomerInfo customerInfo) {
        return new CustomerInfoEmbeddable(
                customerInfo.name(),
                customerInfo.emailAddress().email(),
                customerInfo.deliveryAddress().street(),
                customerInfo.deliveryAddress().number(),
                customerInfo.deliveryAddress().postalCode(),
                customerInfo.deliveryAddress().city(),
                customerInfo.deliveryAddress().country()
        );
    }

    private static OrderItemJpaEntity toOrderItemEntity(OrderItem orderItem, OrderJpaEntity orderEntity) {
        return new OrderItemJpaEntity(
                orderItem.dishId().uuid(),
                orderItem.name(),
                orderItem.quantity(),
                orderItem.price(),
                orderEntity
        );
    }

    private static OrderItem toOrderItem(OrderItemJpaEntity entity) {
        return new OrderItem(
                DishId.of(entity.getId()),
                entity.getName(),
                entity.getPrice(),
                entity.getQuantity()
        );
    }
}
