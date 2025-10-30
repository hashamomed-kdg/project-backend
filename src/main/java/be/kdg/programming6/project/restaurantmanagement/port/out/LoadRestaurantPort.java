package be.kdg.programming6.project.restaurantmanagement.port.out;

import be.kdg.programming6.project.restaurantmanagement.domain.Restaurant;
import be.kdg.programming6.project.restaurantmanagement.domain.valueobject.OwnerId;
import be.kdg.programming6.project.restaurantmanagement.domain.valueobject.RestaurantId;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface LoadRestaurantPort {

    Optional<Restaurant> loadById(RestaurantId restaurantId);

    List<Restaurant> loadAll();

    Optional<Restaurant> loadByOwnerId(OwnerId ownerId);

    List<Restaurant> loadAllRestaurantsWithDraftsWithPublishAtBefore(LocalDateTime time);
}
