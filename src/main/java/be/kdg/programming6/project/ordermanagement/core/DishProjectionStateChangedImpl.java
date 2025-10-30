package be.kdg.programming6.project.ordermanagement.core;

import be.kdg.programming6.project.ordermanagement.domain.DishProjection;
import be.kdg.programming6.project.ordermanagement.port.in.DishProjectionStateChangeCommand;
import be.kdg.programming6.project.ordermanagement.port.in.DishProjectionStateChanged;
import be.kdg.programming6.project.ordermanagement.port.out.LoadDishProjectionPort;
import be.kdg.programming6.project.ordermanagement.port.out.SaveDishProjectionPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class DishProjectionStateChangedImpl implements DishProjectionStateChanged {

    private final LoadDishProjectionPort loadDishProjectionPort;
    private final SaveDishProjectionPort saveDishProjectionPort;


    public DishProjectionStateChangedImpl(LoadDishProjectionPort loadDishProjectionPort, SaveDishProjectionPort saveDishProjectionPort) {
        this.loadDishProjectionPort = loadDishProjectionPort;
        this.saveDishProjectionPort = saveDishProjectionPort;
    }


    @Override
    @Transactional
    public DishProjection project(DishProjectionStateChangeCommand command) {
        DishProjection dishProjection = loadDishProjectionPort.loadDishProjectionById(command.dishId())
                        .orElse(new DishProjection(command.dishId(), command.state()));
        dishProjection.changeAvailability(command.state());
        saveDishProjectionPort.saveDishProjection(dishProjection);
        return dishProjection;
    }

}
