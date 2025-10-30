package be.kdg.programming6.project.restaurantmanagement.adapter.out.jpa.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public record AddressEmbeddable(
        String street,
        String number,
        String postalCode,
        String city,
        String country
) {
}
