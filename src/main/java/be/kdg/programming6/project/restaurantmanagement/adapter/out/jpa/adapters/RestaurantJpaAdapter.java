package be.kdg.programming6.project.restaurantmanagement.adapter.out.jpa.adapters;

import be.kdg.programming6.project.restaurantmanagement.adapter.RestaurantMapper;
import be.kdg.programming6.project.restaurantmanagement.adapter.out.jpa.entity.RestaurantJpaEntity;
import be.kdg.programming6.project.restaurantmanagement.adapter.out.jpa.repository.RestaurantJpaRepository;
import be.kdg.programming6.project.restaurantmanagement.domain.Restaurant;
import be.kdg.programming6.project.restaurantmanagement.domain.valueobject.RestaurantId;
import be.kdg.programming6.project.restaurantmanagement.port.out.LoadRestaurantPort;
import be.kdg.programming6.project.restaurantmanagement.port.out.SaveRestaurantPort;
import be.kdg.programming6.project.restaurantmanagement.port.out.UpdateRestaurantPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Component
public class RestaurantJpaAdapter implements SaveRestaurantPort, LoadRestaurantPort, UpdateRestaurantPort {

    private final RestaurantJpaRepository restaurantRepo;
    private final RestaurantMapper mapper;
    private final Logger log = LoggerFactory.getLogger(RestaurantJpaAdapter.class);

    public RestaurantJpaAdapter(RestaurantJpaRepository restaurantRepo, RestaurantMapper mapper) {
        this.restaurantRepo = restaurantRepo;
        this.mapper = mapper;
    }

    @Override
    public Restaurant saveRestaurant(Restaurant restaurant) {
        RestaurantJpaEntity entity = mapper.toEntity(restaurant);

        restaurantRepo.save(entity);
        return restaurant;
    }

    @Override
    public Optional<Restaurant> loadById(RestaurantId restaurantId) {
        RestaurantJpaEntity entity = restaurantRepo.findById(restaurantId.uuid()).get();
        Restaurant restaurant = mapper.toDomain(entity);
        return Optional.of(restaurant);
    }

    @Override
    public List<Restaurant> loadAll() {
        List<RestaurantJpaEntity> entities = restaurantRepo.findAll();
        return entities.stream().map(mapper::toDomain).toList();
    }

    @Override
    public List<Restaurant> loadAllRestaurantsWithDraftsWithPublishAtBefore(LocalDateTime time) {
        List<RestaurantJpaEntity> entities = restaurantRepo.findAllByDishes_PublishAtBefore(time);
        return entities.stream().map(mapper::toDomain).toList();
    }


    @Override
    public Restaurant updateRestaurant(Restaurant restaurant) {
        RestaurantJpaEntity entity = mapper.toEntity(restaurant);
        restaurantRepo.save(entity);
        return restaurant;
    }
}
