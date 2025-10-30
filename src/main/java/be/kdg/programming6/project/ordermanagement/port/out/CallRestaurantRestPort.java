package be.kdg.programming6.project.ordermanagement.port.out;

import be.kdg.programming6.project.common.commands.OrderDecisionRequest;
import be.kdg.programming6.project.ordermanagement.domain.valueobject.OrderId;
import be.kdg.programming6.project.ordermanagement.domain.valueobject.RestaurantId;

public interface CallRestaurantRestPort {

    void requestForOrderDecision(OrderDecisionRequest request);
}

