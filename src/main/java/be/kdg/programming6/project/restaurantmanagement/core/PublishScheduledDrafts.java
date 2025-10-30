package be.kdg.programming6.project.restaurantmanagement.core;

import be.kdg.programming6.project.restaurantmanagement.domain.Dish;
import be.kdg.programming6.project.restaurantmanagement.domain.Restaurant;
import be.kdg.programming6.project.restaurantmanagement.domain.valueobject.RestaurantId;
import be.kdg.programming6.project.restaurantmanagement.port.out.LoadRestaurantPort;
import be.kdg.programming6.project.restaurantmanagement.port.out.SaveRestaurantPort;
import be.kdg.programming6.project.restaurantmanagement.port.out.UpdateDishPort;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PublishScheduledDrafts {

    private final LoadRestaurantPort loadRestaurantPort;
    private final List<SaveRestaurantPort> saveRestaurantPort;
    private final List<UpdateDishPort> updateDishPort;
    private final Logger log = LoggerFactory.getLogger(PublishScheduledDrafts.class);

    public PublishScheduledDrafts(LoadRestaurantPort loadRestaurantPort, List<SaveRestaurantPort> saveRestaurantPort, List<UpdateDishPort> updateDishPort) {
        this.loadRestaurantPort = loadRestaurantPort;
        this.saveRestaurantPort = saveRestaurantPort;
        this.updateDishPort = updateDishPort;
    }



    @Scheduled(fixedRate = 60000)
    @Transactional
    public void checkToPublishScheduledDrafts() {
        List<Restaurant> restaurants = loadRestaurantPort.loadAllRestaurantsWithDraftsWithPublishAtBefore(LocalDateTime.now()); // more efficient
        for (Restaurant restaurant : restaurants) {
            RestaurantId restaurantId = restaurant.getRestaurantId();
            restaurant.getMenu().publishScheduledDrafts(LocalDateTime.now());
            saveRestaurantPort.forEach(port -> port.saveRestaurant(restaurant));
            for (Dish dish : restaurant.getMenu().getDishes()) {
                updateDishPort.forEach(port -> port.updateDish(restaurantId, dish));
            }
        }
        log.info("Published scheduled drafts");
    }
}
