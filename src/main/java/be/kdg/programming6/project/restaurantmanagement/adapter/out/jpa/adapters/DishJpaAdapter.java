package be.kdg.programming6.project.restaurantmanagement.adapter.out.jpa.adapters;

import be.kdg.programming6.project.restaurantmanagement.adapter.out.jpa.entity.DishJpaEntity;
import be.kdg.programming6.project.restaurantmanagement.adapter.out.jpa.entity.RestaurantJpaEntity;
import be.kdg.programming6.project.restaurantmanagement.adapter.out.jpa.repository.DishJpaRepository;
import be.kdg.programming6.project.restaurantmanagement.adapter.out.jpa.repository.RestaurantJpaRepository;
import be.kdg.programming6.project.restaurantmanagement.domain.Dish;
import be.kdg.programming6.project.restaurantmanagement.domain.valueobject.DishId;
import be.kdg.programming6.project.restaurantmanagement.domain.valueobject.FoodTag;
import be.kdg.programming6.project.restaurantmanagement.domain.valueobject.PictureUrl;
import be.kdg.programming6.project.restaurantmanagement.domain.valueobject.RestaurantId;
import be.kdg.programming6.project.restaurantmanagement.port.out.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class DishJpaAdapter implements LoadDishPort, UpdateDishPort, SaveDishPort, RemoveDishPort, UpdateDraftPort {

    private final DishJpaRepository dishRepo;
    private final RestaurantJpaRepository restaurantRepo;
    private final Logger log = LoggerFactory.getLogger(DishJpaAdapter.class);

    public DishJpaAdapter(DishJpaRepository dishRepo, RestaurantJpaRepository restaurantRepo) {
        this.dishRepo = dishRepo;

        this.restaurantRepo = restaurantRepo;
    }

    //TODO: add mappers

    @Override
    public Optional<Dish> loadById(DishId dishId) {
        DishJpaEntity dishEntity = dishRepo.findById(dishId.uuid()).get();
        Set<FoodTag> tags = Arrays.stream(dishEntity.getTags())
                .collect(Collectors.toSet());

        Dish dish = new Dish(
                DishId.of(dishEntity.getDishId()),
                DishId.of(dishEntity.getParentId()),
                dishEntity.getName(),
                dishEntity.getDishType(),
                tags,
                dishEntity.getDescription(),
                dishEntity.getPrice(),
                new PictureUrl(dishEntity.getPictureUrl()),
                dishEntity.getState(),
                dishEntity.getPublishAt()
        );
        return Optional.of(dish);
    }

    @Override
    public Optional<Dish> loadByName(String name) {
        return Optional.empty();
    }

    @Override
    public List<Dish> loadAll() {
        List<DishJpaEntity> dishEntities = dishRepo.findAll();
        List<Dish> dishes = dishEntities.stream().map(entity -> new Dish(
                DishId.of(entity.getDishId()),
                DishId.of(entity.getParentId()),
                entity.getName(),
                entity.getDishType(),
                Arrays.stream(entity.getTags())
                        .collect(Collectors.toSet()),
                entity.getDescription(),
                entity.getPrice(),
                new PictureUrl(entity.getPictureUrl()),
                entity.getState(),
                entity.getPublishAt())
        ).toList();

        return dishes;
    }

    @Override
    public List<Dish> loadByRestaurantId(UUID restaurantId) {
        List<DishJpaEntity> dishEntities = dishRepo.findAllByRestaurant_Uuid((restaurantId));
        List<Dish> dishes = dishEntities.stream().map(entity -> new Dish(
                DishId.of(entity.getDishId()),
                DishId.of(entity.getParentId()),
                entity.getName(),
                entity.getDishType(),
                Arrays.stream(entity.getTags())
                        .collect(Collectors.toSet()),
                entity.getDescription(),
                entity.getPrice(),
                new PictureUrl(entity.getPictureUrl()),
                entity.getState(),
                entity.getPublishAt())
        ).toList();
        return dishes;
    }

    @Override
    public List<Dish> loadAllDraftsWithPublishAtBefore(LocalDateTime time) {
        List<DishJpaEntity> dishEntities = dishRepo.findAllByPublishAtBefore(time);
        List<Dish> dishes = dishEntities.stream().map(entity -> new Dish(
                DishId.of(entity.getDishId()),
                DishId.of(entity.getParentId()),
                entity.getName(),
                entity.getDishType(),
                Arrays.stream(entity.getTags())
                        .collect(Collectors.toSet()),
                entity.getDescription(),
                entity.getPrice(),
                new PictureUrl(entity.getPictureUrl()),
                entity.getState(),
                entity.getPublishAt())
        ).toList();
        return dishes;
    }

    @Override
    public Dish createDraft(Dish dish) {
        DishJpaEntity ParentEntity = dishRepo.findById(dish.getParentId().get().uuid()).get();
        RestaurantJpaEntity restaurantEntity = ParentEntity.getRestaurant();


        FoodTag[] tags = dish.getTags().stream()
                .map(Object::toString)
                .map(FoodTag::valueOf)
                .toArray(FoodTag[]::new);

        DishJpaEntity entity = new DishJpaEntity(
                dish.getDishId().uuid(),
                dish.getParentId().get().uuid(),
                dish.getName(),
                dish.getDishType(),
                tags,
                dish.getDescription(),
                dish.getPrice(),
                dish.getPictureUrl().url(),
                dish.getState(),
                restaurantEntity
        );

        dishRepo.save(entity);
        return dish;
    }


    @Override
    public Dish changeStateDish(Dish dish) {
        DishJpaEntity entity = dishRepo.findById(dish.getDishId().uuid()).get();
        entity.setState(dish.getState());
        dishRepo.save(entity);
        return dish;
    }

    @Override
    public Dish updateDish(RestaurantId restaurantId, Dish dish) {
        DishJpaEntity entity = dishRepo.findById(dish.getDishId().uuid()).get();
        entity.setName(dish.getName());
        entity.setDishType(dish.getDishType());
        entity.setTags(dish.getTags().stream()
                .map(Object::toString)
                .map(FoodTag::valueOf)
                .toArray(FoodTag[]::new));
        entity.setDescription(dish.getDescription());
        entity.setPrice(dish.getPrice());
        entity.setPictureUrl(dish.getPictureUrl().url());
        entity.setState(dish.getState());
        entity.setRestaurant(restaurantRepo.findById(restaurantId.uuid()).get());
        dishRepo.save(entity);
        return dish;
    }

    @Override
    public Dish updateDraft(Dish dish) {
        DishJpaEntity entity = dishRepo.findById(dish.getDishId().uuid()).get();
        entity.setName(dish.getName());
        entity.setDishType(dish.getDishType());
        entity.setTags(dish.getTags().stream()
                .map(Object::toString)
                .map(FoodTag::valueOf)
                .toArray(FoodTag[]::new));
        entity.setDescription(dish.getDescription());
        entity.setPrice(dish.getPrice());
        entity.setPictureUrl(dish.getPictureUrl().url());
        dishRepo.save(entity);
        return dish;
    }

    @Override
    public Dish updateScheduled(Dish dish) {
        DishJpaEntity entity = dishRepo.findById(dish.getDishId().uuid()).get();
        entity.setPublishAt(dish.getPublishAt().orElse(null));
        dishRepo.save(entity);
        return dish;
    }


    @Override
    public Dish saveDish(RestaurantId restaurantId, Dish dish) {
        FoodTag[] tags = dish.getTags().stream()
                .map(Object::toString)
                .map(FoodTag::valueOf)
                .toArray(FoodTag[]::new);

        DishJpaEntity entity = new DishJpaEntity(
                dish.getDishId().uuid(),
                dish.getName(),
                dish.getDishType(),
                tags,
                dish.getDescription(),
                dish.getPrice(),
                dish.getPictureUrl().url(),
                dish.getState(),
                restaurantRepo.findById(restaurantId.uuid()).get()
        );

        dishRepo.save(entity);
        return dish;
    }


    @Override
    public Dish removeDish(Dish dish) {
        dishRepo.deleteById(dish.getDishId().uuid());
        return dish;
    }
}
