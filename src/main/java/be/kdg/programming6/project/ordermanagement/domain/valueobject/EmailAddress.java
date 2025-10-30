package be.kdg.programming6.project.ordermanagement.domain.valueobject;

public record EmailAddress(String email) {
    public static EmailAddress create(String emailAddress) {
        return new EmailAddress(emailAddress);
    }
    public static EmailAddress of(String emailAddress) {
        return new EmailAddress(emailAddress);
    }
}
