package be.kdg.programming6.project.ordermanagement.adapter.in;

import be.kdg.programming6.project.ordermanagement.adapter.BasketMapper;
import be.kdg.programming6.project.ordermanagement.adapter.OrderMapper;
import be.kdg.programming6.project.ordermanagement.adapter.in.request.AddItemInBasketRequest;
import be.kdg.programming6.project.ordermanagement.adapter.in.request.CustomerInfoDto;
import be.kdg.programming6.project.ordermanagement.adapter.in.response.BasketDto;
import be.kdg.programming6.project.ordermanagement.adapter.in.response.BasketItemDto;
import be.kdg.programming6.project.ordermanagement.adapter.in.response.OrderDto;
import be.kdg.programming6.project.ordermanagement.domain.Basket;
import be.kdg.programming6.project.ordermanagement.domain.Order;
import be.kdg.programming6.project.ordermanagement.domain.valueobject.BasketId;
import be.kdg.programming6.project.ordermanagement.domain.valueobject.DishId;
import be.kdg.programming6.project.ordermanagement.port.in.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final CheckOutBasketUseCase checkOutBasketUseCase;
    private final AddItemInBasketUseCase addItemInBasketUseCase;
    private final RemoveItemFromBasketUseCase removeItemFromBasketUseCase;

    public OrderController(CheckOutBasketUseCase checkOutBasketUseCase, AddItemInBasketUseCase addItemInBasketUseCase, RemoveItemFromBasketUseCase removeItemFromBasketUseCase) {
        this.checkOutBasketUseCase = checkOutBasketUseCase;
        this.addItemInBasketUseCase = addItemInBasketUseCase;
        this.removeItemFromBasketUseCase = removeItemFromBasketUseCase;
    }

    @PatchMapping("/add")
    public ResponseEntity<BasketDto> addItem(@RequestBody AddItemInBasketRequest request) {
        AddItemInBasketCommand command = BasketMapper.toCommand(request);
        Basket basket = addItemInBasketUseCase.addItemInBasket(command);

        return ResponseEntity.ok(BasketMapper.toBasketDto(basket));
    }

    @PatchMapping("/{basketId}/remove")
    public ResponseEntity<BasketDto> removeItem(@PathVariable("basketId") UUID basketId, @RequestBody DishId dishId) {
        RemoveItemFromBasketCommand command = new RemoveItemFromBasketCommand(dishId, BasketId.of(basketId));
        Basket basket = removeItemFromBasketUseCase.removeItemFromBasket(command);

        return ResponseEntity.ok(BasketMapper.toBasketDto(basket));
    }

    @PostMapping("/{basketId}")
    public ResponseEntity<OrderDto> checkOut(@PathVariable("basketId") UUID basketId, @RequestBody CustomerInfoDto customerInfo) {
        CheckOutBasketCommand command = new CheckOutBasketCommand(
                BasketId.of(basketId),
                OrderMapper.toCustomerInfo(customerInfo));
        Order order = checkOutBasketUseCase.checkOutBasket(command);

        return ResponseEntity.ok(OrderMapper.toOrderDto(order));
    }

}
