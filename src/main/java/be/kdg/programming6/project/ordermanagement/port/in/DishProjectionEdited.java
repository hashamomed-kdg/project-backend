package be.kdg.programming6.project.ordermanagement.port.in;

import be.kdg.programming6.project.ordermanagement.domain.DishProjection;

public interface DishProjectionEdited {

    DishProjection project(DishProjectionEditedCommand command);
}
