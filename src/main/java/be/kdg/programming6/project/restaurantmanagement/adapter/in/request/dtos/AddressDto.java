package be.kdg.programming6.project.restaurantmanagement.adapter.in.request.dtos;

public record AddressDto(
        String street,
        String number,
        String postalCode,
        String city,
        String country
) {
}
