package be.kdg.programming6.project.restaurantmanagement.domain;

import be.kdg.programming6.project.restaurantmanagement.domain.valueobject.EmailAddress;
import be.kdg.programming6.project.restaurantmanagement.domain.valueobject.OwnerId;


public class Owner {

    private OwnerId ownerId;
    private EmailAddress email;
    private String password;
    private String fullName;

    public Owner(OwnerId ownerId, EmailAddress email, String password, String fullName) {
        this.ownerId = ownerId;
        this.email = email;
        this.password = password;
        this.fullName = fullName;
    }

    public OwnerId getOwnerId() { return ownerId; }
    public EmailAddress getEmail() { return email; }
    public String getPassword() { return password; }
    public String getFullName() { return fullName; }

}
