package be.kdg.programming6.project.restaurantmanagement.port.in;

import be.kdg.programming6.project.restaurantmanagement.domain.Dish;

public interface ChangeStateDishUseCase {

    Dish changeStateDish(ChangeStateDishCommand command);
}
