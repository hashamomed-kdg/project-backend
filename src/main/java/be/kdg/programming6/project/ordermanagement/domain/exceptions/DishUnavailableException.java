package be.kdg.programming6.project.ordermanagement.domain.exceptions;

public class DishUnavailableException extends RuntimeException {
    public DishUnavailableException(String message) {
        super(message);
    }
}
