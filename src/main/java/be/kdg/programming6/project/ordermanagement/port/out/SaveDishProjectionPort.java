package be.kdg.programming6.project.ordermanagement.port.out;

import be.kdg.programming6.project.ordermanagement.domain.DishProjection;

public interface SaveDishProjectionPort {

    DishProjection saveDishProjection(DishProjection dishProjection);
}
