package be.kdg.programming6.project.ordermanagement.adapter.out.jpa.entity;

import be.kdg.programming6.project.ordermanagement.domain.valueobject.OrderStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
public class OrderJpaEntity {

    @Id
    private UUID id;

    @Column(name = "restaurant_id")
    private UUID restaurantId;

    @Embedded
    private CustomerInfoEmbeddable customerInfo;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItemJpaEntity> orderItems;

    protected OrderJpaEntity() {}

    public OrderJpaEntity(UUID id, UUID restaurantId, CustomerInfoEmbeddable customerInfo, BigDecimal totalPrice, OrderStatus orderStatus) {
        this.id = id;
        this.restaurantId = restaurantId;
        this.customerInfo = customerInfo;
        this.totalPrice = totalPrice;
        this.orderStatus = orderStatus;
    }

    public OrderJpaEntity(UUID id, UUID restaurantId, CustomerInfoEmbeddable customerInfo, BigDecimal totalPrice, OrderStatus orderStatus, List<OrderItemJpaEntity> orderItems) {
        this.id = id;
        this.restaurantId = restaurantId;
        this.customerInfo = customerInfo;
        this.totalPrice = totalPrice;
        this.orderStatus = orderStatus;
        this.orderItems = orderItems;
    }

    public UUID getId() {
        return id;
    }

    public UUID getRestaurantId() {
        return restaurantId;
    }

    public CustomerInfoEmbeddable getCustomerInfo() {
        return customerInfo;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public List<OrderItemJpaEntity> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemJpaEntity> orderItems) {
        this.orderItems = orderItems;
    }
}
