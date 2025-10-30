package be.kdg.programming6.project.ordermanagement.domain.valueobject;

public record CustomerInfo(
        String name,
        DeliveryAddress deliveryAddress,
        EmailAddress emailAddress
) {
}
