package be.kdg.programming6.project.ordermanagement.core;

import be.kdg.programming6.project.ordermanagement.domain.DishProjection;
import be.kdg.programming6.project.ordermanagement.port.in.DishProjectionEdited;
import be.kdg.programming6.project.ordermanagement.port.in.DishProjectionEditedCommand;
import be.kdg.programming6.project.ordermanagement.port.out.LoadDishProjectionPort;
import be.kdg.programming6.project.ordermanagement.port.out.SaveDishProjectionPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class DishProjectionEditedImpl implements DishProjectionEdited {

    private final LoadDishProjectionPort loadDishProjectionPort;
    private final SaveDishProjectionPort saveDishProjectionPort;


    public DishProjectionEditedImpl(LoadDishProjectionPort loadDishProjectionPort, SaveDishProjectionPort saveDishProjectionPort) {
        this.loadDishProjectionPort = loadDishProjectionPort;
        this.saveDishProjectionPort = saveDishProjectionPort;
    }


    @Override
    @Transactional
    public DishProjection project(DishProjectionEditedCommand command) {
        DishProjection dishProjection = loadDishProjectionPort.loadDishProjectionById(command.dishId())
                .orElse(new DishProjection(command.dishId(), command.newPrice()));

        dishProjection.changePrice(command.newPrice());
        saveDishProjectionPort.saveDishProjection(dishProjection);
        return dishProjection;
    }
}
