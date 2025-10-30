package be.kdg.programming6.project.ordermanagement.port.in;

import be.kdg.programming6.project.ordermanagement.domain.valueobject.DeliveryAddress;
import be.kdg.programming6.project.ordermanagement.domain.valueobject.OrderId;
import be.kdg.programming6.project.ordermanagement.domain.valueobject.RestaurantId;

public record RequestOrderDecisionCommand(
        OrderId orderId,
        RestaurantId restaurantId,
        String street,
        String number,
        String postalCode,
        String city
){
}

