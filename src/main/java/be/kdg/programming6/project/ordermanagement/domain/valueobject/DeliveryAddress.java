package be.kdg.programming6.project.ordermanagement.domain.valueobject;

public record DeliveryAddress(
        String street,
        String number,
        String postalCode,
        String city,
        String country
) {
}
