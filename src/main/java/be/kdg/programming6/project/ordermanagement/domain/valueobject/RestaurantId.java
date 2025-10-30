package be.kdg.programming6.project.ordermanagement.domain.valueobject;

import java.util.UUID;

public record RestaurantId(UUID uuid) {

    public static RestaurantId of(UUID uuid) {return new RestaurantId(uuid);}


}
