package be.kdg.programming6.project.restaurantmanagement.domain.valueobject;

import java.util.UUID;

public record DishId(UUID uuid) {
    public static DishId create() {return new DishId(UUID.randomUUID());}
    public static DishId of(UUID uuid) {return new DishId(uuid);}
}
