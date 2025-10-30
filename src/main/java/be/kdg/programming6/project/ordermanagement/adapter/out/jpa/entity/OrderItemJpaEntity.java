package be.kdg.programming6.project.ordermanagement.adapter.out.jpa.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "order_items")
public class OrderItemJpaEntity {


    @GeneratedValue(generator = "UUID")
    @Id
    private UUID id;

    @Column(name = "dish_id")
    private UUID dishId;

    private String name;

    private long quantity;

    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private OrderJpaEntity order;

    protected OrderItemJpaEntity() {
    }

    public OrderItemJpaEntity(UUID dishId, String name, long quantity, BigDecimal price, OrderJpaEntity order) {
        this.dishId = dishId;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.order = order;
    }

    public UUID getId() {
        return id;
    }

    public UUID getDishId() {
        return dishId;
    }

    public String getName() {
        return name;
    }

    public long getQuantity() {
        return quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public OrderJpaEntity getOrder() {
        return order;
    }

    public void setOrder(OrderJpaEntity order) {
        this.order = order;
    }
}
