package be.kdg.programming6.project.restaurantmanagement.domain.valueobject;

public record Address(
    String street,
    String number,
    String postalCode,
    String city,
    String country
) {}
