package be.kdg.programming6.project.ordermanagement.adapter.out.jpa.adapter;

import be.kdg.programming6.project.ordermanagement.adapter.out.jpa.entity.projection.RestaurantProjectionJpaEntity;
import be.kdg.programming6.project.ordermanagement.adapter.out.jpa.repository.RestaurantProjectionJpaRepository;
import be.kdg.programming6.project.ordermanagement.domain.RestaurantProjection;
import be.kdg.programming6.project.ordermanagement.domain.valueobject.RestaurantId;
import be.kdg.programming6.project.ordermanagement.port.out.LoadRestaurantProjectionPort;
import be.kdg.programming6.project.ordermanagement.port.out.SaveRestaurantProjectionPort;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RestaurantProjectionJpaAdapter implements LoadRestaurantProjectionPort, SaveRestaurantProjectionPort {

    private final RestaurantProjectionJpaRepository restaurantRepo;

    public RestaurantProjectionJpaAdapter(RestaurantProjectionJpaRepository restaurantRepo) {
        this.restaurantRepo = restaurantRepo;
    }

    @Override
    public Optional<RestaurantProjection> loadRestaurantProjectionById(RestaurantId restaurantId) {
        return restaurantRepo.findById(restaurantId.uuid()).map(
                entity -> new RestaurantProjection(
                        RestaurantId.of(entity.getRestaurantId()),
                        entity.getOpeningHours(),
                        entity.getStatus()
                )
        );
    }

    @Override
    public RestaurantProjection saveRestaurantProjection(RestaurantProjection restaurantProjection) {
        restaurantRepo.save(new RestaurantProjectionJpaEntity(
                restaurantProjection.getRestaurantId().uuid(),
                restaurantProjection.getOpeningHours(),
                restaurantProjection.getStatus()
        ));
        return restaurantProjection;
    }
}
