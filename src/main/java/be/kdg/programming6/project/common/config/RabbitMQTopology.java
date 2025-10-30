package be.kdg.programming6.project.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.*;



@Configuration
public class RabbitMQTopology {

    public static final String KDG_EVENTS = "kdg.events";

    public static final String ORDER_PICKED_UP_QUEUE = "delivery.order.pickedup";
    public static final String ORDER_LOCATION_QUEUE = "delivery.order.location";
    public static final String ORDER_DELIVERED_QUEUE = "delivery.order.delivered";

    @Bean
    TopicExchange deliveryExchange() {
        return ExchangeBuilder
                .topicExchange(KDG_EVENTS)
                .durable(true)
                .build();
    }


    @Bean
    Queue orderPickedUpQueue() {
        return QueueBuilder
                .durable(ORDER_PICKED_UP_QUEUE)
                .build();
    }

    @Bean
    Queue orderLocationQueue() {
        return QueueBuilder
                .durable(ORDER_LOCATION_QUEUE)
                .build();
    }

    @Bean
    Queue orderDeliveredQueue() {
        return QueueBuilder
                .durable(ORDER_DELIVERED_QUEUE)
                .build();
    }


    @Bean
    Binding bindPickedUpToExchange() {
        return BindingBuilder
                .bind(orderPickedUpQueue())
                .to(deliveryExchange())
                .with("delivery.*.order.pickedup.v1");
    }

    @Bean
    Binding bindLocationToExchange() {
        return BindingBuilder
                .bind(orderLocationQueue())
                .to(deliveryExchange())
                .with("delivery.*.order.location.v1");
    }

    @Bean
    Binding bindDeliveredToExchange() {
        return BindingBuilder
                .bind(orderDeliveredQueue())
                .to(deliveryExchange())
                .with("delivery.*.order.delivered.v1");
    }
}






