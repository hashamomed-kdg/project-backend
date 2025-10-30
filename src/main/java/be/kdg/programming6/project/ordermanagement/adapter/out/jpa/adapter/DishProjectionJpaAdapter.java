package be.kdg.programming6.project.ordermanagement.adapter.out.jpa.adapter;

import be.kdg.programming6.project.ordermanagement.adapter.out.jpa.entity.projection.DishProjectionJpaEntity;
import be.kdg.programming6.project.ordermanagement.adapter.out.jpa.repository.DishProjectionJpaRepository;
import be.kdg.programming6.project.ordermanagement.domain.DishProjection;
import be.kdg.programming6.project.ordermanagement.domain.valueobject.DishId;
import be.kdg.programming6.project.ordermanagement.port.out.LoadDishProjectionPort;
import be.kdg.programming6.project.ordermanagement.port.out.SaveDishProjectionPort;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DishProjectionJpaAdapter implements LoadDishProjectionPort, SaveDishProjectionPort {

    private final DishProjectionJpaRepository dishRepo;

    public DishProjectionJpaAdapter(DishProjectionJpaRepository dishRepo) {
        this.dishRepo = dishRepo;
    }


    @Override
    public Optional<DishProjection> loadDishProjectionById(DishId dishId) {
        return dishRepo.findById(dishId.uuid())
                .map(entity -> new DishProjection(
                        DishId.of(entity.getId()),
                        entity.getPrice(),
                        entity.getState()
                ));
    }

    @Override
    public DishProjection saveDishProjection(DishProjection dishProjection) {
        dishRepo.save(
                new DishProjectionJpaEntity(
                        dishProjection.getDishId().uuid(),
                        dishProjection.getPrice(),
                        dishProjection.getState()
                )
        );
        return dishProjection;
    }
}
