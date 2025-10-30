package be.kdg.programming6.project.ordermanagement.adapter.out.jpa.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "basket_items")
public class BasketItemJpaEntity {

    @GeneratedValue(generator = "UUID")
    @Id
    private UUID id;

    @Column(name = "dish_id")
    private UUID dishId;

    @Column(name = "restaurant_id")
    private UUID restaurantId;

    private String name;

    private BigDecimal price;

    @Column(name = "quantity")
    private long quantity;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "basket_id")
    private BasketJpaEntity basket;

    protected BasketItemJpaEntity() {}

    public BasketItemJpaEntity(UUID dishId, UUID restaurantId, String name, BigDecimal price, long quantity, BasketJpaEntity basket) {
        this.dishId = dishId;
        this.restaurantId = restaurantId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.basket = basket;
    }

    public UUID getId() {
        return id;
    }

    public UUID getDishId() {
        return dishId;
    }

    public UUID getRestaurantId() {
        return restaurantId;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public long getQuantity() {
        return quantity;
    }

    public BasketJpaEntity getBasket() {
        return basket;
    }



}
