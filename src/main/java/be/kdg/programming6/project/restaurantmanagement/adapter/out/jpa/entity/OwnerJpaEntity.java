package be.kdg.programming6.project.restaurantmanagement.adapter.out.jpa.entity;

import be.kdg.programming6.project.restaurantmanagement.domain.valueobject.OwnerId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "owners")
public class OwnerJpaEntity {

    @Id
    private UUID ownerId;

    private String email;
    private String password;

    @Column(name = "full_name")
    private String fullName;

    protected OwnerJpaEntity() {}

    public OwnerJpaEntity(UUID ownerId, String email, String password, String fullName) {
        this.ownerId = ownerId;
        this.email = email;
        this.password = password;
        this.fullName = fullName;
    }


    public UUID getOwnerId() { return ownerId; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getFullName() { return fullName; }
}

