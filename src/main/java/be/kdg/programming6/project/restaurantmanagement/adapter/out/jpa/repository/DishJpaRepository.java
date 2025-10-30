package be.kdg.programming6.project.restaurantmanagement.adapter.out.jpa.repository;

import be.kdg.programming6.project.restaurantmanagement.adapter.out.jpa.entity.DishJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface DishJpaRepository extends JpaRepository<DishJpaEntity, UUID> {
    List<DishJpaEntity> findAllByRestaurant_Uuid(UUID restaurantUuid);

    List<DishJpaEntity> findAllByPublishAtBefore(LocalDateTime publishAtBefore);
}
