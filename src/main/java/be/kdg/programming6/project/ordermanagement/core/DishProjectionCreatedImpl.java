package be.kdg.programming6.project.ordermanagement.core;

import be.kdg.programming6.project.ordermanagement.domain.DishProjection;
import be.kdg.programming6.project.ordermanagement.port.in.DishProjectionCreated;
import be.kdg.programming6.project.ordermanagement.port.in.DishProjectionCreatedCommand;
import be.kdg.programming6.project.ordermanagement.port.out.SaveDishProjectionPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class DishProjectionCreatedImpl implements DishProjectionCreated {

    private final SaveDishProjectionPort saveDishProjectionPort;

    public DishProjectionCreatedImpl(SaveDishProjectionPort saveDishProjectionPort) {
        this.saveDishProjectionPort = saveDishProjectionPort;
    }

    @Override
    @Transactional
    public DishProjection project(DishProjectionCreatedCommand command) {
        DishProjection dishProjection = new DishProjection(
                command.dishId(),
                command.price(),
                command.state()
        );

        saveDishProjectionPort.saveDishProjection(dishProjection);
        return dishProjection;
    }
}
