package be.kdg.programming6.project.ordermanagement.adapter.out.jpa.entity.projection;

import be.kdg.programming6.project.ordermanagement.domain.valueobject.DishState;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "dish_projections")
public class DishProjectionJpaEntity {

    @Id
    private UUID id;

    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private DishState state;

    protected DishProjectionJpaEntity() {}

    public DishProjectionJpaEntity(UUID id, BigDecimal price, DishState state) {
        this.id = id;
        this.price = price;
        this.state = state;
    }

    public UUID getId() {
        return id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public DishState getState() {
        return state;
    }


}
