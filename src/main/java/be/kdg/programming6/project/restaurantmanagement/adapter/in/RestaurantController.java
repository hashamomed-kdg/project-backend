package be.kdg.programming6.project.restaurantmanagement.adapter.in;

import be.kdg.programming6.project.common.commands.OrderDecisionRequest;
import be.kdg.programming6.project.restaurantmanagement.adapter.DishMapper;
import be.kdg.programming6.project.restaurantmanagement.adapter.RestaurantMapper;
import be.kdg.programming6.project.restaurantmanagement.adapter.in.request.*;
import be.kdg.programming6.project.restaurantmanagement.adapter.in.response.DishDto;
import be.kdg.programming6.project.restaurantmanagement.adapter.in.response.RestaurantDto;
import be.kdg.programming6.project.restaurantmanagement.core.FindDishesUseCaseImpl;
import be.kdg.programming6.project.restaurantmanagement.domain.Dish;
import be.kdg.programming6.project.restaurantmanagement.domain.Restaurant;
import be.kdg.programming6.project.restaurantmanagement.domain.valueobject.DishState;
import be.kdg.programming6.project.restaurantmanagement.domain.valueobject.RestaurantId;
import be.kdg.programming6.project.restaurantmanagement.port.in.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

    private final RestaurantMapper mapper;
    private final DishMapper dishMapper;
    private final CreateRestaurantUseCase createRestaurantUseCase;
    private final EditDishUseCase editDishUseCase;
    private final ChangeStateDishUseCase changeStateDishUseCase;
    private final CreateDishUseCase createDishUseCase;
    private final ApplyAllDraftsUseCase applyAllDraftsUseCase;
    private final ScheduleDraftsUseCase scheduleDraftsUseCase;
    private final ChangeOpeningHoursUseCase changeOpeningHoursUseCase;
    private final ChangeRestaurantStatusUseCase changeRestaurantStatusUseCase;
    private final DecideOrderUseCase decideOrderUseCase;
    private final MarkOrderReadyUseCase markOrderReadyUseCase;
    private final FindDishesUseCaseImpl findDishesUseCase;

    public RestaurantController(RestaurantMapper mapper, DishMapper dishMapper, CreateRestaurantUseCase createRestaurantUseCase, EditDishUseCase editDishUseCase, ChangeStateDishUseCase changeStateDishUseCase, CreateDishUseCase createDishUseCase, ApplyAllDraftsUseCase applyAllDraftsUseCase, ScheduleDraftsUseCase scheduleDraftsUseCase, ChangeOpeningHoursUseCase changeOpeningHoursUseCase, ChangeRestaurantStatusUseCase changeRestaurantStatusUseCase, DecideOrderUseCase decideOrderUseCase, MarkOrderReadyUseCase markOrderReadyUseCase, FindDishesUseCaseImpl findDishesUseCase) {
        this.mapper = mapper;
        this.dishMapper = dishMapper;
        this.createRestaurantUseCase = createRestaurantUseCase;
        this.editDishUseCase = editDishUseCase;
        this.changeStateDishUseCase = changeStateDishUseCase;
        this.createDishUseCase = createDishUseCase;
        this.applyAllDraftsUseCase = applyAllDraftsUseCase;
        this.scheduleDraftsUseCase = scheduleDraftsUseCase;
        this.changeOpeningHoursUseCase = changeOpeningHoursUseCase;
        this.changeRestaurantStatusUseCase = changeRestaurantStatusUseCase;
        this.decideOrderUseCase = decideOrderUseCase;
        this.markOrderReadyUseCase = markOrderReadyUseCase;
        this.findDishesUseCase = findDishesUseCase;
    }

    @GetMapping("/{restaurantId}/dishes")
    public ResponseEntity<List<DishDto>> getDishes(@PathVariable UUID restaurantId) {
        FindAllDishesByRestaurantCommand command = new FindAllDishesByRestaurantCommand(RestaurantId.of(restaurantId));
        List<Dish> dishes = findDishesUseCase.findAllDishesByRestaurant(command);

        return ResponseEntity.ok(dishes.stream()
                .map(dishMapper::toDto)
                .collect(Collectors.toList()));

    }

    @PostMapping()
    public ResponseEntity<RestaurantDto> createRestaurant(@RequestBody CreateRestaurantRequest request) {
        CreateRestaurantCommand command = mapper.toCommand(request);
        Restaurant restaurant = createRestaurantUseCase.createRestaurant(command);

        return ResponseEntity.ok(mapper.toDto(restaurant));
    }

    @PostMapping("/{restaurantId}/menu/createDish")
    public ResponseEntity<DishDto> createDish(@PathVariable UUID restaurantId, @RequestBody CreateDishRequest request) {
        CreateDishCommand command = dishMapper.toCreateCommand(restaurantId, request);
        Dish dish = createDishUseCase.createDish(command);

        return ResponseEntity.ok(dishMapper.toDto(dish));
    }

    @PatchMapping("/{restaurantId}/menu/{dishId}/editDish")
    public ResponseEntity<DishDto> editDish(@PathVariable UUID restaurantId, @PathVariable UUID dishId, @RequestBody EditDishRequest request) {
        EditDishCommand command = dishMapper.toEditCommand(restaurantId, dishId, request);
        Dish dish = editDishUseCase.editDish(command);

        return ResponseEntity.ok(dishMapper.toDto(dish));
    }

    @PatchMapping("/{restaurantId}/menu/{dishId}/changeStateDish")
    public ResponseEntity<DishDto> changeStateDish(@PathVariable UUID restaurantId, @PathVariable UUID dishId, @RequestBody ChangeStateDishRequest request) {
        ChangeStateDishCommand command = new ChangeStateDishCommand(restaurantId, dishId, DishState.valueOf(request.state()));
        Dish dish = changeStateDishUseCase.changeStateDish(command);

        return ResponseEntity.ok(dishMapper.toDto(dish));
    }

    @PatchMapping("/{restaurantId}/menu/applyAllDrafts")
    public ResponseEntity<RestaurantDto> applyAllDrafts(@PathVariable UUID restaurantId) {
        ApplyAllDraftsCommand command = new ApplyAllDraftsCommand(restaurantId);
        Restaurant restaurant = applyAllDraftsUseCase.applyAllDrafts(command);

        return ResponseEntity.ok(mapper.toDto(restaurant));
    }

    @PatchMapping("/{restaurantId}/menu/scheduleDrafts")
    public ResponseEntity<RestaurantDto> scheduleDrafts(@PathVariable UUID restaurantId, @RequestBody ScheduleDraftsRequest request) {
        ScheduleDraftsCommand command = new ScheduleDraftsCommand(restaurantId, dishMapper.toDishIds(request.dishIds()) , request.publishTime());
        Restaurant restaurant = scheduleDraftsUseCase.scheduleDrafts(command);

        return ResponseEntity.ok(mapper.toDto(restaurant));

    }

    @PatchMapping("/{restaurantId}/changeOpeningHours")
    public ResponseEntity<RestaurantDto> changeOpeningHours(@PathVariable UUID restaurantId, @RequestBody ChangeOpeningHoursRequest request) {
        ChangeOpeningHoursCommand command = new ChangeOpeningHoursCommand(restaurantId, mapper.openingHoursDtoToOpeningHours(request.openingHours()));
        Restaurant restaurant = changeOpeningHoursUseCase.changeOpeningHours(command);

        return ResponseEntity.ok(mapper.toDto(restaurant));
    }

    @PatchMapping("/{restaurantId}/changeRestaurantStatus")
    public ResponseEntity<RestaurantDto> changeRestaurantStatus(@PathVariable UUID restaurantId, @RequestBody ChangeRestaurantStatusRequest request) {
        ChangeRestaurantStatusCommand command = new ChangeRestaurantStatusCommand(restaurantId, mapper.manualStatusToRestaurantStatus(request.manualStatus()));
        Restaurant restaurant = changeRestaurantStatusUseCase.changeRestaurantStatus(command);

        return ResponseEntity.ok(mapper.toDto(restaurant));
    }

    @PostMapping("/orders/decide")
    public ResponseEntity<RestaurantDto> decideOrder(@RequestBody DecideOrderRequest request) {
        DecideOrderCommand command = new DecideOrderCommand(
                request.decisionRequest().orderId(),
                request.decisionRequest().restaurantId(),
                request.accepted(),
                request.reason(),
                request.decisionRequest().deliveryAddress().street(),
                request.decisionRequest().deliveryAddress().number(),
                request.decisionRequest().deliveryAddress().postalCode(),
                request.decisionRequest().deliveryAddress().city()
        );
        Restaurant restaurant = decideOrderUseCase.decideOrder(command);

        return ResponseEntity.ok(mapper.toDto(restaurant));
    }

    @PostMapping("/{restaurantId}/orders/markReady")
    public ResponseEntity<Void> markOrderReady (@PathVariable UUID restaurantId, @RequestBody MarkOrderReadyRequest request) {
        MarkOrderReadyCommand command = new MarkOrderReadyCommand(request.orderId(), RestaurantId.of(restaurantId));
        markOrderReadyUseCase.markOrderReady(command);

        return ResponseEntity.ok().build();
    }







}
