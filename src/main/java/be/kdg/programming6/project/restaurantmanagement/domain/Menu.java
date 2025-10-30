package be.kdg.programming6.project.restaurantmanagement.domain;

import be.kdg.programming6.project.restaurantmanagement.domain.exceptions.DishPublishLimitExceededException;
import be.kdg.programming6.project.restaurantmanagement.domain.valueobject.DishId;
import be.kdg.programming6.project.restaurantmanagement.domain.valueobject.DishState;

import java.time.LocalDateTime;
import java.util.*;

public class Menu {

    private final long MAX_DISHES = 10;
    private List<Dish> dishes = new ArrayList<>();


    public void addDish(Dish dish) {
        if (dish.isPublished() && getPublishedDishesCount() >= MAX_DISHES) {
            throw new DishPublishLimitExceededException("You can't publish more than 10 dishes at a time.");
        }
        dishes.add(dish);
    }

    public Dish editDish(Dish dish){
        Optional<Dish> existingDraft = dishes.stream()
                .filter(d -> d.getParentId().equals(dish.getParentId())).findFirst();

        if (existingDraft.isPresent()){
            Dish draft = existingDraft.get();
            draft.setName(dish.getName());
            draft.setDishType(dish.getDishType());
            draft.setTags(dish.getTags());
            draft.setDescription(dish.getDescription());
            draft.setPrice(dish.getPrice());
            draft.setPictureUrl(dish.getPictureUrl());
            return draft;
        }
        else {
            Dish newDraft = new Dish(
                    dish.getParentId().get(),
                    dish.getName(),
                    dish.getDishType(),
                    dish.getTags(),
                    dish.getDescription(),
                    dish.getPrice(),
                    dish.getPictureUrl()
            );
            dishes.add(newDraft);
            return newDraft;
        }
    }



    public void changeDishState(Dish dish, DishState state){
        if (state == DishState.PUBLISHED && getPublishedDishesCount() >= MAX_DISHES){
            throw new DishPublishLimitExceededException("You can't publish more than 10 dishes at a time.");
        }
        dish.changeState(state);
    }


    public void publishScheduledDrafts(LocalDateTime now){
        Iterator<Dish> iterator = dishes.iterator();
        while (iterator.hasNext()){
            Dish draft = iterator.next();
            if (draft.isDraft() && draft.isPublishedAtBefore(now)){
                Dish originalDish = getDishById(draft.getParentId().get());
                originalDish.updateFromDraft(draft);
                draft.setParentId(null);
                iterator.remove();
            }
        }
    }



    public void applyAllDrafts(){
        Iterator<Dish> iterator = dishes.iterator();
        while (iterator.hasNext()) {
            Dish dish = iterator.next();
            if (dish.isDraft()) {
                Dish originalDish = getDishById(dish.getParentId().get());
                originalDish.updateFromDraft(dish);
                dish.setParentId(null);
                iterator.remove();
            }
        }
    }

    private Dish getDishById(DishId dishId){
        return dishes.stream().filter(d -> d.getDishId().equals(dishId)).findFirst().orElse(null);
    }

    private long getPublishedDishesCount(){
        return dishes.stream().filter(Dish::isPublished).count();
    }



    public List<Dish> getDishes() {
        return dishes;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "dishes=" + dishes +
                '}';
    }
}
