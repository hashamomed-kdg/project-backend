package be.kdg.programming6.project.restaurantmanagement.adapter.in.request;

public record SignupRequest(
        String email, String password, String fullName
) {
}
