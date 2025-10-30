package be.kdg.programming6.project.ordermanagement.core;

import be.kdg.programming6.project.ordermanagement.domain.Basket;
import be.kdg.programming6.project.ordermanagement.domain.DishProjection;
import be.kdg.programming6.project.ordermanagement.domain.RestaurantProjection;
import be.kdg.programming6.project.ordermanagement.port.in.RemoveItemFromBasketCommand;
import be.kdg.programming6.project.ordermanagement.port.in.RemoveItemFromBasketUseCase;
import be.kdg.programming6.project.ordermanagement.port.out.LoadBasketPort;
import be.kdg.programming6.project.ordermanagement.port.out.LoadDishProjectionPort;
import be.kdg.programming6.project.ordermanagement.port.out.LoadRestaurantProjectionPort;
import be.kdg.programming6.project.ordermanagement.port.out.SaveBasketPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RemoveItemFromBasketUseCaseImpl implements RemoveItemFromBasketUseCase {

    private final LoadBasketPort loadBasketPort;
    private final SaveBasketPort saveBasketPort;
    private final LoadDishProjectionPort loadDishProjectionPort;
    private final LoadRestaurantProjectionPort loadRestaurantProjectionPort;

    public RemoveItemFromBasketUseCaseImpl(LoadBasketPort loadBasketPort, SaveBasketPort saveBasketPort, LoadDishProjectionPort loadDishProjectionPort, LoadRestaurantProjectionPort loadRestaurantProjectionPort) {
        this.loadBasketPort = loadBasketPort;
        this.saveBasketPort = saveBasketPort;
        this.loadDishProjectionPort = loadDishProjectionPort;
        this.loadRestaurantProjectionPort = loadRestaurantProjectionPort;
    }

    @Override
    @Transactional
    public Basket removeItemFromBasket(RemoveItemFromBasketCommand command) {
        Basket basket = loadBasketPort.loadBasketById(command.basketId()).get();
        RestaurantProjection restaurantProjection =
                loadRestaurantProjectionPort.loadRestaurantProjectionById(basket.getRestaurantId()).get();
        List<DishProjection> remainingItems = basket.getBasketItems().stream()
                .filter(item -> !item.getDishId().equals(command.dishId()))
                .map(item -> loadDishProjectionPort.loadDishProjectionById(item.getDishId()).get())
                .toList();

        basket.removeItem(command.dishId());

        basket.revalidate(restaurantProjection, remainingItems);

        saveBasketPort.saveBasket(basket);
        return basket;
    }
}
