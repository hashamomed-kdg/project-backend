package be.kdg.programming6.project.ordermanagement.adapter.in.request;

public record CustomerInfoDto(
        String name,
        String email,
        String street,
        String number,
        String postalCode,
        String city,
        String country
) {
}
