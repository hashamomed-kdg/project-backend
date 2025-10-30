package be.kdg.programming6.project.ordermanagement.domain;

import be.kdg.programming6.project.ordermanagement.domain.valueobject.OpeningHours;
import be.kdg.programming6.project.ordermanagement.domain.valueobject.RestaurantId;
import be.kdg.programming6.project.ordermanagement.domain.valueobject.RestaurantStatus;

import java.time.LocalDateTime;

public class RestaurantProjection {

    private RestaurantId restaurantId;
    private OpeningHours openingHours;
    private RestaurantStatus status;

    public RestaurantProjection(RestaurantId restaurantId, OpeningHours openingHours, RestaurantStatus status) {
        this.restaurantId = restaurantId;
        this.openingHours = openingHours;
        this.status = status;
    }

    public RestaurantProjection(RestaurantId restaurantId, OpeningHours openingHours) {
        this.restaurantId = restaurantId;
        this.openingHours = openingHours;
    }

    public RestaurantProjection(RestaurantId restaurantId, RestaurantStatus status) {
        this.restaurantId = restaurantId;
        this.status = status;
    }

    public boolean isOpen(LocalDateTime time) {
        if (status == RestaurantStatus.CLOSED) {
            return false;
        }
        return openingHours.isOpenAt(time);
    }

    public RestaurantId getRestaurantId() {
        return restaurantId;
    }

    public OpeningHours getOpeningHours() {
        return openingHours;
    }

    public RestaurantStatus getStatus() {
        return status;
    }
}
