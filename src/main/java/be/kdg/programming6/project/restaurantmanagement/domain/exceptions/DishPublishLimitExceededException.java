package be.kdg.programming6.project.restaurantmanagement.domain.exceptions;

public class DishPublishLimitExceededException extends RuntimeException {
    public DishPublishLimitExceededException(String message) {
        super(message);
    }
}
