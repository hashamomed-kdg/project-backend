package be.kdg.programming6.project.ordermanagement.domain.exceptions;

public class RestaurantClosedException extends RuntimeException {
    public RestaurantClosedException(String message) {
        super(message);
    }
}
