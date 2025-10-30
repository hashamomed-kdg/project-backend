package be.kdg.programming6.project.ordermanagement.port.in;


import be.kdg.programming6.project.ordermanagement.domain.valueobject.DishId;
import be.kdg.programming6.project.ordermanagement.domain.valueobject.DishState;

import java.math.BigDecimal;

public record DishProjectionEditedCommand(
        DishId dishId,
        BigDecimal newPrice,
        DishState newState
){
}
