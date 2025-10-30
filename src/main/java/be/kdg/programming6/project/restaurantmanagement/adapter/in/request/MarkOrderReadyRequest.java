package be.kdg.programming6.project.restaurantmanagement.adapter.in.request;

import java.util.UUID;

public record MarkOrderReadyRequest(
        UUID orderId
){
}
