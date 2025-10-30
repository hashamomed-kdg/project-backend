package be.kdg.programming6.project.restaurantmanagement.domain;

import be.kdg.programming6.project.common.events.restaurant.DishCreatedEvent;
import be.kdg.programming6.project.common.events.restaurant.DishEditedEvent;
import be.kdg.programming6.project.common.events.restaurant.DishStateChangeEvent;
import be.kdg.programming6.project.common.events.DomainEvent;
import be.kdg.programming6.project.restaurantmanagement.domain.valueobject.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class Dish {

    private DishId dishId;
    private DishId parentId;
    private String name;
    private DishType dishType;
    private Set<FoodTag> tags;
    private String description;
    private BigDecimal price;
    private PictureUrl pictureUrl;
    private DishState state;
    private LocalDateTime publishAt;

    // TODO: change the implementation of drafts so it can be UNPUBLISHED as well

    private final List<DomainEvent> domainEvents = new ArrayList<>();

    public Dish(DishId dishId, DishId parentId, String name, DishType dishType, Set<FoodTag> tags, String description, BigDecimal price, PictureUrl pictureUrl, DishState state, LocalDateTime publishAt) {
        this.dishId = dishId;
        this.parentId = parentId;
        this.name = name;
        this.dishType = dishType;
        this.tags = tags;
        this.description = description;
        this.price = price;
        this.pictureUrl = pictureUrl;
        this.state = state;
        this.publishAt = publishAt;
    }


        public Dish(DishId dishId, String name, DishType dishType, Set<FoodTag> tags, String description, BigDecimal price, PictureUrl pictureUrl, DishState state) {
        this.dishId = dishId;
        this.name = name;
        this.dishType = dishType;
        this.tags = tags;
        this.description = description;
        this.price = price;
        this.pictureUrl = pictureUrl;
        this.state = state;

        this.domainEvents.add(new DishCreatedEvent(this.dishId.uuid(), this.price, this.state.toString()));
    }

    // for creating drafts
    public Dish(DishId parentId, String name, DishType dishType, Set<FoodTag> tags, String description, BigDecimal price, PictureUrl pictureUrl) {
        this.dishId = DishId.create();
        this.parentId = parentId;
        this.name = name;
        this.dishType = dishType;
        this.tags = tags;
        this.description = description;
        this.price = price;
        this.pictureUrl = pictureUrl;
        this.state = DishState.DRAFT;
    }

    public void updateFromDraft(Dish draft) {
        this.name = draft.getName();
        this.dishType = draft.getDishType();
        this.tags = draft.getTags();
        this.description = draft.getDescription();
        this.price = draft.getPrice();
        this.pictureUrl = draft.getPictureUrl();
        this.state = DishState.PUBLISHED;
        this.domainEvents.add(new DishEditedEvent(
                this.dishId.uuid(),
                this.name,
                this.dishType.name(),
                this.tags.stream().map(Objects::toString).collect(Collectors.toSet()),
                this.description,
                this.price,
                this.pictureUrl.url(),
                this.state.toString()
        ));
    }

    public void changeState(DishState state) {
        if (isDraft()){
            throw new IllegalStateException("A draft can't change state");
        }
        this.state = state;
        this.domainEvents.add(new DishStateChangeEvent(this.dishId.uuid(), state.toString()));
    }

    public void schedulePublication(LocalDateTime time) {
        if (state != DishState.DRAFT) {
            throw new IllegalStateException("Only draft dishes can be scheduled");
        }
        if (time.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Publish time must be in the future");
        }
        this.publishAt = time;
    }





    public boolean isPublished() {
        return this.state == DishState.PUBLISHED;
    }

    public boolean isDraft() {
        return this.state == DishState.DRAFT;
    }

    public boolean isPublishedAtBefore(LocalDateTime time) {
        return this.publishAt != null && this.publishAt.isBefore(time);
    }


    public DishId getDishId() {
        return dishId;
    }

    public String getName() {
        return name;
    }

    public DishType getDishType() {
        return dishType;
    }

    public Set<FoodTag> getTags() {
        return tags;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public PictureUrl getPictureUrl() {
        return pictureUrl;
    }

    public DishState getState() {
        return state;
    }

    public Optional<LocalDateTime> getPublishAt() {
        if (publishAt == null) {
            return Optional.empty();
        } else {
            return Optional.of(publishAt);
        }
    }

    public Optional<DishId> getParentId() {
        if (parentId == null) {
            return Optional.empty();
        } else {
            return Optional.of(parentId);
        }
    }

    public List<DomainEvent> getDomainEvents() {
        return domainEvents;
    }

    //TODO: replace setters with either constructors or name the setters differently

    public void setPublishAt(LocalDateTime publishAt) {
        this.publishAt = publishAt;
    }

    public void setDishId(DishId dishId) {
        this.dishId = dishId;
    }

    public void setParentId(DishId parentId) {
        this.parentId = parentId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDishType(DishType dishType) {
        this.dishType = dishType;
    }

    public void setTags(Set<FoodTag> tags) {
        this.tags = tags;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setPictureUrl(PictureUrl pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public void setState(DishState state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + dishId +
                ", parentId=" + parentId +
                ", name='" + name + '\'' +
                ", dishType=" + dishType +
                ", tags=" + tags +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", pictureUrl=" + pictureUrl +
                ", state=" + state +
                ", publishAt=" + publishAt +
                '}';
    }
}
