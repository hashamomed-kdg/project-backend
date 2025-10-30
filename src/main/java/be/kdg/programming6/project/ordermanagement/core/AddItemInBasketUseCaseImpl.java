package be.kdg.programming6.project.ordermanagement.core;

import be.kdg.programming6.project.ordermanagement.domain.Basket;
import be.kdg.programming6.project.ordermanagement.domain.BasketItem;
import be.kdg.programming6.project.ordermanagement.domain.DishProjection;
import be.kdg.programming6.project.ordermanagement.domain.RestaurantProjection;
import be.kdg.programming6.project.ordermanagement.domain.exceptions.DishUnavailableException;
import be.kdg.programming6.project.ordermanagement.domain.exceptions.RestaurantClosedException;
import be.kdg.programming6.project.ordermanagement.domain.valueobject.BasketId;
import be.kdg.programming6.project.ordermanagement.port.in.AddItemInBasketCommand;
import be.kdg.programming6.project.ordermanagement.port.in.AddItemInBasketUseCase;
import be.kdg.programming6.project.ordermanagement.port.out.LoadBasketPort;
import be.kdg.programming6.project.ordermanagement.port.out.LoadDishProjectionPort;
import be.kdg.programming6.project.ordermanagement.port.out.LoadRestaurantProjectionPort;
import be.kdg.programming6.project.ordermanagement.port.out.SaveBasketPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AddItemInBasketUseCaseImpl implements AddItemInBasketUseCase {

    private final LoadBasketPort loadBasketPort;
    private final LoadDishProjectionPort loadDishProjectionPort;
    private final LoadRestaurantProjectionPort loadRestaurantProjectionPort;
    private final SaveBasketPort saveBasketPort;

    public AddItemInBasketUseCaseImpl(LoadBasketPort loadBasketPort, LoadDishProjectionPort loadDishProjectionPort, LoadRestaurantProjectionPort loadRestaurantProjectionPort, SaveBasketPort saveBasketPort) {
        this.loadBasketPort = loadBasketPort;
        this.loadDishProjectionPort = loadDishProjectionPort;
        this.loadRestaurantProjectionPort = loadRestaurantProjectionPort;
        this.saveBasketPort = saveBasketPort;
    }

    @Override
    @Transactional
    public Basket addItemInBasket(AddItemInBasketCommand command) {
        Basket basket = loadBasketPort.loadBasketById(command.basketId())
                .orElse(new Basket(BasketId.create(), command.restaurantId()));

        RestaurantProjection restaurantProjection = loadRestaurantProjectionPort
                .loadRestaurantProjectionById(command.restaurantId()).get();
        if (!restaurantProjection.isOpen(LocalDateTime.now())){ // decided to validate these here and not in the basket domain class
            throw new RestaurantClosedException("Restaurant is closed at the moment");
        }

        DishProjection dishProjection = loadDishProjectionPort.loadDishProjectionById(command.dishId()).get();
        if (!dishProjection.isAvailable()){
            throw new DishUnavailableException("Dish is not available at the moment");
        }


        basket.addItem(new BasketItem(
                command.dishId(),
                command.restaurantId(),
                command.name(),
                command.price(),
                command.quantity()
        ));

        saveBasketPort.saveBasket(basket);
        return basket;
    }
}
