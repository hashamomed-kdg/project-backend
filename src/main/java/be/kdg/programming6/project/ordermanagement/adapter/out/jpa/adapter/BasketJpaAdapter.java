package be.kdg.programming6.project.ordermanagement.adapter.out.jpa.adapter;

import be.kdg.programming6.project.ordermanagement.adapter.BasketMapper;
import be.kdg.programming6.project.ordermanagement.adapter.out.jpa.entity.BasketJpaEntity;
import be.kdg.programming6.project.ordermanagement.adapter.out.jpa.repository.BasketJpaRepository;
import be.kdg.programming6.project.ordermanagement.domain.Basket;
import be.kdg.programming6.project.ordermanagement.domain.valueobject.BasketId;
import be.kdg.programming6.project.ordermanagement.port.out.LoadBasketPort;
import be.kdg.programming6.project.ordermanagement.port.out.SaveBasketPort;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BasketJpaAdapter implements LoadBasketPort, SaveBasketPort {

    private final BasketJpaRepository basketRepo;

    public BasketJpaAdapter(BasketJpaRepository basketRepo) {
        this.basketRepo = basketRepo;
    }


    @Override
    public Optional<Basket> loadBasketById(BasketId basketId) {
        return basketRepo.findById(basketId.uuid()).map(BasketMapper::toDomain);
    }

    @Override
    public Basket saveBasket(Basket basket) {
        BasketJpaEntity entity = BasketMapper.toEntity(basket);
        basketRepo.save(entity);
        return basket;
    }
}
