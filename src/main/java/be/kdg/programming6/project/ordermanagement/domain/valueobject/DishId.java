package be.kdg.programming6.project.ordermanagement.domain.valueobject;

import java.util.UUID;

public record DishId(UUID uuid) {

    public static DishId of(UUID uuid) {return new DishId(uuid);}
}
