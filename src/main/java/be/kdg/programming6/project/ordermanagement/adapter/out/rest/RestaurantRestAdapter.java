package be.kdg.programming6.project.ordermanagement.adapter.out.rest;

import be.kdg.programming6.project.common.commands.OrderDecisionRequest;
import be.kdg.programming6.project.ordermanagement.domain.valueobject.OrderId;
import be.kdg.programming6.project.ordermanagement.domain.valueobject.RestaurantId;
import be.kdg.programming6.project.ordermanagement.port.out.CallRestaurantRestPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class RestaurantRestAdapter implements CallRestaurantRestPort {

    private final Logger log = LoggerFactory.getLogger(RestaurantRestAdapter.class);

    @Override
    public void requestForOrderDecision(OrderDecisionRequest request) {
        RestClient restClient = RestClient.builder()
                .baseUrl("http://localhost:8080/api/restaurants/orders/decide")
                .build();

        var response = restClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve().toBodilessEntity();

        log.info("Complete response: Status={}, Headers={}, Location={}",
                response.getStatusCode(),
                response.getHeaders(),
                response.getHeaders().getLocation());
    }
}
