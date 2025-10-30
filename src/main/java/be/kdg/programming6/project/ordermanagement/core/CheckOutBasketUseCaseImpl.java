package be.kdg.programming6.project.ordermanagement.core;

import be.kdg.programming6.project.ordermanagement.domain.Basket;
import be.kdg.programming6.project.ordermanagement.domain.DishProjection;
import be.kdg.programming6.project.ordermanagement.domain.Order;
import be.kdg.programming6.project.ordermanagement.domain.RestaurantProjection;
import be.kdg.programming6.project.ordermanagement.domain.exceptions.DishUnavailableException;
import be.kdg.programming6.project.ordermanagement.domain.exceptions.RestaurantClosedException;
import be.kdg.programming6.project.ordermanagement.port.in.CheckOutBasketCommand;
import be.kdg.programming6.project.ordermanagement.port.in.CheckOutBasketUseCase;
import be.kdg.programming6.project.ordermanagement.port.out.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CheckOutBasketUseCaseImpl implements CheckOutBasketUseCase {

    private final LoadBasketPort loadBasketPort;
    private final SaveBasketPort saveBasketPort;
    private final List<SaveOrderPort> saveOrderPort;
    private final LoadDishProjectionPort loadDishProjectionPort;
    private final LoadRestaurantProjectionPort loadRestaurantProjectionPort;

    public CheckOutBasketUseCaseImpl(LoadBasketPort loadBasketPort, SaveBasketPort saveBasketPort, List<SaveOrderPort> saveOrderPort, LoadDishProjectionPort loadDishProjectionPort, LoadRestaurantProjectionPort loadRestaurantProjectionPort) {
        this.loadBasketPort = loadBasketPort;
        this.saveBasketPort = saveBasketPort;
        this.saveOrderPort = saveOrderPort;
        this.loadDishProjectionPort = loadDishProjectionPort;
        this.loadRestaurantProjectionPort = loadRestaurantProjectionPort;
    }

    @Override
    @Transactional
    public Order checkOutBasket(CheckOutBasketCommand command) {
        Basket basket = loadBasketPort.loadBasketById(command.basketId()).get();
        RestaurantProjection restaurantProjection =
                loadRestaurantProjectionPort.loadRestaurantProjectionById(basket.getRestaurantId()).get();

        if (!restaurantProjection.isOpen(LocalDateTime.now())){
            basket.markInvalid();
            throw new RestaurantClosedException("Restaurant is closed at the moment");
        }

        basket.getBasketItems().forEach(item -> {
            DishProjection dish = loadDishProjectionPort.loadDishProjectionById(item.getDishId()).get();
            if (!dish.isAvailable()) {
                basket.markInvalid();
                throw new DishUnavailableException("Dish" + item.getName() + "is not available at the moment");
            }
            item.updatePrice(dish.getPrice()); // update item price to current dish price
        });

        Order order = basket.checkOut(command.customerInfo());
        saveBasketPort.saveBasket(basket);
        saveOrderPort.forEach(port -> port.saveOrder(order));
        return order;
    }
}
