package be.kdg.programming6.project.ordermanagement.adapter.out.jpa.adapter;

import be.kdg.programming6.project.ordermanagement.adapter.OrderMapper;
import be.kdg.programming6.project.ordermanagement.adapter.out.jpa.entity.OrderJpaEntity;
import be.kdg.programming6.project.ordermanagement.adapter.out.jpa.repository.OrderJpaRepository;
import be.kdg.programming6.project.ordermanagement.domain.Order;
import be.kdg.programming6.project.ordermanagement.domain.valueobject.OrderId;
import be.kdg.programming6.project.ordermanagement.port.out.LoadOrderPort;
import be.kdg.programming6.project.ordermanagement.port.out.SaveOrderPort;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class OrderJpaAdapter implements SaveOrderPort, LoadOrderPort {

    private final OrderJpaRepository orderRepo;

    public OrderJpaAdapter(OrderJpaRepository orderRepo) {
        this.orderRepo = orderRepo;
    }

    @Override
    public Optional<Order> loadOrderById(OrderId orderId) {
        return orderRepo.findById(orderId.uuid()).map(OrderMapper::toDomain);
    }

    @Override
    public Order saveOrder(Order order) {
        OrderJpaEntity entity = OrderMapper.toEntity(order);
        orderRepo.save(entity);
        return order;
    }
}
