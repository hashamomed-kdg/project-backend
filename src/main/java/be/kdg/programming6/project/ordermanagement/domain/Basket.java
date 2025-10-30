package be.kdg.programming6.project.ordermanagement.domain;

import be.kdg.programming6.project.common.events.DomainEvent;
import be.kdg.programming6.project.ordermanagement.domain.valueobject.*;

import javax.xml.validation.Validator;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Basket {

    private BasketId basketId;
    private RestaurantId restaurantId;
    private List<BasketItem> basketItems;
    private BasketStatus basketStatus;

    private final List<DomainEvent> domainEvents = new ArrayList<>();

    public Basket(BasketId basketId, RestaurantId restaurantId) {
        this.basketId = basketId;
        this.restaurantId = restaurantId;
        this.basketItems = new ArrayList<>();
        this.basketStatus = BasketStatus.ACTIVE;
    }

    public Basket(BasketId basketId, RestaurantId restaurantId, List<BasketItem> basketItems, BasketStatus basketStatus) {
        this.basketId = basketId;
        this.restaurantId = restaurantId;
        this.basketItems = basketItems;
        this.basketStatus = basketStatus;
    }

    public void addItem(BasketItem item) {
        if (!item.getRestaurantId().equals(restaurantId)) {
            throw new IllegalArgumentException("Can't have dishes from different restaurants in the same basket");
        }
        basketItems.stream()
                .filter(bi -> bi.getDishId().equals(item.getDishId()))
                .findFirst()
                .ifPresent(bi -> bi.addQuantity(item.getQuantity()));

        this.basketItems.add(item);
    }

    public void removeItem(DishId dishId) {
        basketItems.stream()
                .filter(bi -> bi.getDishId().equals(dishId))
                .findFirst()
                .ifPresent(bi -> bi.removeQuantity(bi.getQuantity()));

        basketItems.removeIf(bi -> bi.getQuantity() <= 0);
    }

    public void revalidate(RestaurantProjection restaurantProjection, List<DishProjection> dishProjections){
        boolean isValid = restaurantProjection.isOpen(LocalDateTime.now()) &&
                dishProjections.stream().allMatch(DishProjection::isAvailable);

        if (isValid){
            this.basketStatus = BasketStatus.ACTIVE;
        } else {
            markInvalid();
        }
    }

    public void markInvalid() { // for checkout
        basketStatus = BasketStatus.INVALID;
    }



    public BigDecimal calculateTotalPrice() {
        return basketItems.stream()
                .map(BasketItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Order checkOut(CustomerInfo customerInfo) {
        ensureActive();
        if (basketItems.isEmpty()) {
            throw new IllegalStateException("Cannot checkout an empty basket");
        }

        this.basketStatus = BasketStatus.CHECKED_OUT;

        BigDecimal total = calculateTotalPrice();

        Order order = new Order(
                OrderId.create(),
                restaurantId,
                customerInfo,
                basketItems.stream()
                    .map(bi -> new OrderItem(
                            bi.getDishId(),
                            bi.getName(),
                            bi.getPrice(),
                            bi.getQuantity()
                    )).toList(),
                total
        );

        basketItems.clear();

        return order;

    }

    private void ensureActive() {
        if (basketStatus != BasketStatus.ACTIVE) {
            throw new IllegalStateException("One or more items cannot be ordered or basket has been checked out");
        }
    }




    public BasketId getBasketId() {
        return basketId;
    }

    public RestaurantId getRestaurantId() {
        return restaurantId;
    }

    public List<BasketItem> getBasketItems() {
        return basketItems;
    }

    public BasketStatus getBasketStatus() {
        return basketStatus;
    }
}
