package be.kdg.programming6.project.restaurantmanagement.port.out;

import be.kdg.programming6.project.restaurantmanagement.domain.Dish;

public interface UpdateDraftPort {

    Dish updateDraft(Dish dish);

    Dish updateScheduled(Dish dish);
}
