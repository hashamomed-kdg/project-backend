package be.kdg.programming6.project.ordermanagement.domain.valueobject;

import java.util.UUID;

public record BasketId(UUID uuid) {
    public static BasketId create() {
        return new BasketId(UUID.randomUUID());
    }
    public static BasketId of(UUID uuid) {
        return new BasketId(uuid);
    }
}
