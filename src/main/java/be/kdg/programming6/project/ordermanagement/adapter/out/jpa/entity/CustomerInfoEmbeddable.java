package be.kdg.programming6.project.ordermanagement.adapter.out.jpa.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public record CustomerInfoEmbeddable(
        String name,
        String emailAddress,
        String street,
        String number,
        String postalCode,
        String city,
        String country
) {
}
