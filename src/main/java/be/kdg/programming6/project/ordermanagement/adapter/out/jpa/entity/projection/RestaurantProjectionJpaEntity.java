package be.kdg.programming6.project.ordermanagement.adapter.out.jpa.entity.projection;

import be.kdg.programming6.project.ordermanagement.domain.valueobject.OpeningHours;
import be.kdg.programming6.project.ordermanagement.domain.valueobject.RestaurantStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity
@Table(name = "restaurant_projections")
public class RestaurantProjectionJpaEntity {

    @Id
    @Column(name = "restaurant_id")
    private UUID restaurantId;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "opening_hours", columnDefinition = "jsonb")
    private OpeningHours openingHours;

    @Enumerated(EnumType.STRING)
    private RestaurantStatus status;

    protected RestaurantProjectionJpaEntity() {}

    public RestaurantProjectionJpaEntity(UUID restaurantId, OpeningHours openingHours, RestaurantStatus status) {
        this.restaurantId = restaurantId;
        this.openingHours = openingHours;
        this.status = status;
    }

    public UUID getRestaurantId() {
        return restaurantId;
    }

    public OpeningHours getOpeningHours() {
        return openingHours;
    }

    public RestaurantStatus getStatus() {
        return status;
    }
}
