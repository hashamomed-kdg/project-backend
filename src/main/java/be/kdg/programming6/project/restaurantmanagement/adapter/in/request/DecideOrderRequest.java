package be.kdg.programming6.project.restaurantmanagement.adapter.in.request;

import be.kdg.programming6.project.common.commands.OrderDecisionRequest;

public record DecideOrderRequest (
        OrderDecisionRequest decisionRequest,
        boolean accepted,
        String reason
)
{
}
