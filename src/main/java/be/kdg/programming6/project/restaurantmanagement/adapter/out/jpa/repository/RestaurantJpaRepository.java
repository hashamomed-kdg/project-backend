package be.kdg.programming6.project.restaurantmanagement.adapter.out.jpa.repository;

import be.kdg.programming6.project.restaurantmanagement.adapter.out.jpa.entity.RestaurantJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RestaurantJpaRepository extends JpaRepository<RestaurantJpaEntity, UUID> {

    List<RestaurantJpaEntity> findAllByDishes_PublishAtBefore(LocalDateTime publishAtBefore);

    Optional<RestaurantJpaEntity> findByOwner(UUID owner);
}
