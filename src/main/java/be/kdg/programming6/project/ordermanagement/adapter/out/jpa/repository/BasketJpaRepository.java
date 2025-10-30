package be.kdg.programming6.project.ordermanagement.adapter.out.jpa.repository;

import be.kdg.programming6.project.ordermanagement.adapter.out.jpa.entity.BasketJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BasketJpaRepository extends JpaRepository<BasketJpaEntity, UUID> {

}
