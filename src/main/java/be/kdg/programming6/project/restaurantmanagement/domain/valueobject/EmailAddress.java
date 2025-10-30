package be.kdg.programming6.project.restaurantmanagement.domain.valueobject;

public record EmailAddress(String email) {

    public static EmailAddress create(String email) {return new EmailAddress(email);}
    public static EmailAddress of(String email) {return new EmailAddress(email);}
}
