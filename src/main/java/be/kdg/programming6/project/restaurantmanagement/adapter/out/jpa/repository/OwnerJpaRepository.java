package be.kdg.programming6.project.restaurantmanagement.adapter.out.jpa.repository;

import be.kdg.programming6.project.restaurantmanagement.adapter.out.jpa.entity.OwnerJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OwnerJpaRepository extends JpaRepository<OwnerJpaEntity, UUID> {

    Optional<OwnerJpaEntity> findByEmail(String email);
}
