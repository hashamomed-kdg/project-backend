package be.kdg.programming6.project.ordermanagement.adapter.in;

import be.kdg.programming6.project.common.events.restaurant.*;
import be.kdg.programming6.project.ordermanagement.domain.valueobject.*;
import be.kdg.programming6.project.ordermanagement.port.in.*;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ProjectionsListener {

    private final DishProjectionStateChanged dishProjectionStateChanged;
    private final DishProjectionEdited dishProjectionEdited;
    private final RestaurantProjectionOpeningHoursChanged restaurantProjectionOpeningHoursChanged;
    private final RestaurantProjectionStatusChanged restaurantProjectionStatusChanged;
    private final RestaurantProjectionCreated restaurantProjectionCreated;
    private final DishProjectionCreated dishProjectionCreated;

    public ProjectionsListener(DishProjectionStateChanged dishProjectionStateChanged, DishProjectionEdited dishProjectionEdited, RestaurantProjectionOpeningHoursChanged restaurantProjectionOpeningHoursChanged, RestaurantProjectionStatusChanged restaurantProjectionStatusChanged, RestaurantProjectionCreated restaurantProjectionCreated, DishProjectionCreated dishProjectionCreated) {
        this.dishProjectionStateChanged = dishProjectionStateChanged;
        this.dishProjectionEdited = dishProjectionEdited;
        this.restaurantProjectionOpeningHoursChanged = restaurantProjectionOpeningHoursChanged;
        this.restaurantProjectionStatusChanged = restaurantProjectionStatusChanged;
        this.restaurantProjectionCreated = restaurantProjectionCreated;
        this.dishProjectionCreated = dishProjectionCreated;
    }

    @EventListener(DishStateChangeEvent.class)
    public void dishStateChanged(DishStateChangeEvent event) {
        DishProjectionStateChangeCommand command = new DishProjectionStateChangeCommand(
                        DishId.of(event.dish()),
                        DishState.valueOf(event.state())
                );
        dishProjectionStateChanged.project(command);
    }

    @EventListener(DishEditedEvent.class)
    public void dishEdited(DishEditedEvent event) {
        DishProjectionEditedCommand command = new DishProjectionEditedCommand(
                DishId.of(event.dish()),
                event.price(),
                DishState.valueOf(event.state())
        );
        dishProjectionEdited.project(command);

    }

    @EventListener(RestaurantStatusChangedEvent.class)
    public void restaurantStatusChanged(RestaurantStatusChangedEvent event) {
        RestaurantProjectionStatusChangeCommand command = new RestaurantProjectionStatusChangeCommand(
                RestaurantId.of(event.restaurant()),
                RestaurantStatus.valueOf(event.status())
        );
        restaurantProjectionStatusChanged.project(command);
    }


    @EventListener(RestaurantOpeningsHoursChangedEvent.class)
    public void restaurantOpeningHoursChanged(RestaurantOpeningsHoursChangedEvent event) {
        RestaurantProjectionOpeningHoursChangedCommand command = new RestaurantProjectionOpeningHoursChangedCommand(
                RestaurantId.of(event.restaurant()),
                toOpeningHours(event.openingHours())
        );
        restaurantProjectionOpeningHoursChanged.project(command);
    }

    @EventListener(RestaurantCreatedEvent.class)
    public void restaurantCreated(RestaurantCreatedEvent event) {
        RestaurantProjectionCreatedCommand command = new RestaurantProjectionCreatedCommand(
                RestaurantId.of(event.restaurant()),
                toOpeningHours(event.openingHours()),
                RestaurantStatus.valueOf(event.status())
        );
        restaurantProjectionCreated.project(command);
    }

    @EventListener(DishCreatedEvent.class)
    public void dishCreated(DishCreatedEvent event) {
        DishProjectionCreatedCommand command = new DishProjectionCreatedCommand(
                DishId.of(event.dishId()),
                event.price(),
                DishState.valueOf(event.state())
        );
        dishProjectionCreated.project(command);
    }

    private OpeningHours toOpeningHours(Map<String, List<TimeRangeDtoForEvent>> openingHours) {
        Map<DayOfWeek, List<TimeRange>> mapped = openingHours.entrySet().stream()
                .collect(Collectors.toMap(
                        e -> DayOfWeek.valueOf(e.getKey()),
                        e -> {
                            List<TimeRangeDtoForEvent> timeRanges = e.getValue();
                            if (timeRanges == null || timeRanges.isEmpty()) {
                                return null;
                            }

                            return timeRanges.stream().map(tr -> new TimeRange(
                                    LocalTime.parse(tr.open()),
                                    LocalTime.parse(tr.close())
                            )).toList();
                        }
                ));
        return new OpeningHours(mapped);
    }


}
