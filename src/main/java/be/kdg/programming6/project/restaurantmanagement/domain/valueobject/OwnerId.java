package be.kdg.programming6.project.restaurantmanagement.domain.valueobject;

import java.util.UUID;

public record OwnerId(UUID uuid) {

    public static OwnerId create() {return new OwnerId(UUID.randomUUID());}

    public static OwnerId of(UUID uuid) {return new OwnerId(uuid);}

}
