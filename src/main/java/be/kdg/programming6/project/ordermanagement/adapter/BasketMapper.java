package be.kdg.programming6.project.ordermanagement.adapter;

import be.kdg.programming6.project.ordermanagement.adapter.in.request.AddItemInBasketRequest;
import be.kdg.programming6.project.ordermanagement.adapter.in.response.BasketDto;
import be.kdg.programming6.project.ordermanagement.adapter.in.response.BasketItemDto;
import be.kdg.programming6.project.ordermanagement.adapter.out.jpa.entity.BasketItemJpaEntity;
import be.kdg.programming6.project.ordermanagement.adapter.out.jpa.entity.BasketJpaEntity;
import be.kdg.programming6.project.ordermanagement.domain.Basket;
import be.kdg.programming6.project.ordermanagement.domain.valueobject.BasketId;
import be.kdg.programming6.project.ordermanagement.domain.BasketItem;
import be.kdg.programming6.project.ordermanagement.domain.valueobject.DishId;
import be.kdg.programming6.project.ordermanagement.domain.valueobject.RestaurantId;
import be.kdg.programming6.project.ordermanagement.port.in.AddItemInBasketCommand;

import java.util.List;
import java.util.UUID;

public class BasketMapper {

    public static BasketJpaEntity toEntity (Basket basket) {
        BasketJpaEntity entity = new BasketJpaEntity(
                basket.getBasketId().uuid(),
                basket.getRestaurantId().uuid(),
                basket.getBasketStatus()
        );
        entity.setBasketItems(basket.getBasketItems().stream()
                .map(Item -> toBasketItemEntity(Item, entity)).toList());
        return entity;
    }

    public static Basket toDomain (BasketJpaEntity entity) {
        return new Basket(
                BasketId.of(entity.getId()),
                RestaurantId.of(entity.getRestaurantId()),
                entity.getBasketItems().stream()
                        .map(BasketMapper::toBasketItem).toList(),
                entity.getBasketStatus()
        );
    }

    public static AddItemInBasketCommand toCommand(AddItemInBasketRequest item){
        return new AddItemInBasketCommand(
                BasketId.of(item.basketId()),
                DishId.of(item.dishId()),
                RestaurantId.of(item.restaurantId()),
                item.name(),
                item.price(),
                item.quantity()
        );

    }

    public static BasketDto toBasketDto(Basket basket){
        return new BasketDto(
                basket.getBasketId().uuid(),
                basket.getRestaurantId().uuid(),
                basket.getBasketItems().stream()
                        .map(item -> new BasketItemDto(
                                item.getDishId().uuid(),
                                item.getRestaurantId().uuid(),
                                item.getName(),
                                item.getPrice(),
                                item.getQuantity()
                        )).toList(),
                basket.getBasketStatus().name()
        );
    }



    private static BasketItemJpaEntity toBasketItemEntity (BasketItem item, BasketJpaEntity basketEntity) {
        return new BasketItemJpaEntity(
                item.getDishId().uuid(),
                item.getRestaurantId().uuid(),
                item.getName(),
                item.getPrice(),
                item.getQuantity(),
                basketEntity
        );
    }

    private static BasketItem toBasketItem (BasketItemJpaEntity entity) {
        return new BasketItem(
                DishId.of(entity.getDishId()),
                RestaurantId.of(entity.getRestaurantId()),
                entity.getName(),
                entity.getPrice(),
                entity.getQuantity()
        );
    }
}
