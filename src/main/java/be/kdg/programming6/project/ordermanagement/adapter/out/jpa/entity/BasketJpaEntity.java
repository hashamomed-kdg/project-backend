package be.kdg.programming6.project.ordermanagement.adapter.out.jpa.entity;


import be.kdg.programming6.project.ordermanagement.domain.valueobject.BasketStatus;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "baskets")
public class BasketJpaEntity {

    @Id
    private UUID id;

    @Column(name = "restaurant_id")
    private UUID restaurantId;

    @Column(name = "basket_status")
    @Enumerated(EnumType.STRING)
    private BasketStatus basketStatus;

    @Column(name = "basket_items")
    @OneToMany(mappedBy = "basket", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BasketItemJpaEntity> basketItems = new ArrayList<>();

    protected BasketJpaEntity() {}

    public BasketJpaEntity(UUID id, UUID restaurantId, BasketStatus basketStatus) {
        this.id = id;
        this.restaurantId = restaurantId;
        this.basketStatus = basketStatus;
    }

    public BasketJpaEntity(UUID id, UUID restaurantId, BasketStatus basketStatus, List<BasketItemJpaEntity> basketItems) {
        this.id = id;
        this.restaurantId = restaurantId;
        this.basketStatus = basketStatus;
        this.basketItems = basketItems;
    }

    public UUID getId() {
        return id;
    }

    public UUID getRestaurantId() {
        return restaurantId;
    }

    public BasketStatus getBasketStatus() {
        return basketStatus;
    }

    public List<BasketItemJpaEntity> getBasketItems() {
        return basketItems;
    }

    public void setBasketItems(List<BasketItemJpaEntity> basketItems) {
        this.basketItems = basketItems;
    }
}
