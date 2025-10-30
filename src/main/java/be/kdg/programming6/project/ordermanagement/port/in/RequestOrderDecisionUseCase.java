package be.kdg.programming6.project.ordermanagement.port.in;

import be.kdg.programming6.project.ordermanagement.domain.Order;

public interface RequestOrderDecisionUseCase {
    Order requestDecision(RequestOrderDecisionCommand command);
}
