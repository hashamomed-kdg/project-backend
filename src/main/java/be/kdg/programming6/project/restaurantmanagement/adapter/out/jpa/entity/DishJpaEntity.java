package be.kdg.programming6.project.restaurantmanagement.adapter.out.jpa.entity;

import be.kdg.programming6.project.restaurantmanagement.domain.valueobject.DishState;
import be.kdg.programming6.project.restaurantmanagement.domain.valueobject.DishType;
import be.kdg.programming6.project.restaurantmanagement.domain.valueobject.FoodTag;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;


@Entity
@Table(name = "dishes")
public class DishJpaEntity {

    @Id
    private UUID dishId;

    @Column(name = "parent_id")
    private UUID parentId;

    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "dish_type")
    private DishType dishType;


    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(columnDefinition = "text[]")
    @Enumerated(EnumType.STRING)
    private FoodTag[] tags;

    private String description;

    private BigDecimal price;

    @Column(name = "picture_url")
    private String pictureUrl;

    @Enumerated(EnumType.STRING)
    private DishState state;

    @Column(name = "publish_at")
    private LocalDateTime publishAt;

    @ManyToOne(optional = false)
    @JoinColumn(name = "restaurant_uuid")
    private RestaurantJpaEntity restaurant;

    protected DishJpaEntity() {
    }

    public DishJpaEntity(UUID dishId, String name, DishType dishType, FoodTag[] tags, String description, BigDecimal price, String pictureUrl, DishState state, RestaurantJpaEntity restaurant) {
        this.dishId = dishId;
        this.name = name;
        this.dishType = dishType;
        this.tags = tags;
        this.description = description;
        this.price = price;
        this.pictureUrl = pictureUrl;
        this.state = state;
        this.restaurant = restaurant;
    }


    public DishJpaEntity(UUID dishId, UUID parentId, String name, DishType dishType, FoodTag[] tags, String description, BigDecimal price, String pictureUrl, DishState state, RestaurantJpaEntity restaurant) {
        this.dishId = dishId;
        this.parentId = parentId;
        this.name = name;
        this.dishType = dishType;
        this.tags = tags;
        this.description = description;
        this.price = price;
        this.pictureUrl = pictureUrl;
        this.state = state;
        this.restaurant = restaurant;
    }

    public DishJpaEntity(UUID dishId, UUID parentId, String name, DishType dishType, FoodTag[] tags, String description, BigDecimal price, String pictureUrl, DishState state, LocalDateTime publishAt) {
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

    public UUID getDishId() {
        return dishId;
    }

    public String getName() {
        return name;
    }

    public DishType getDishType() {
        return dishType;
    }

    public FoodTag[] getTags() {
        return tags;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public DishState getState() {
        return state;
    }

    public RestaurantJpaEntity getRestaurant() {
        return restaurant;
    }

    public UUID getParentId() {
        return parentId;
    }

    public LocalDateTime getPublishAt() {
        return publishAt;
    }

    public void setState(DishState state) {
        this.state = state;
    }

    public void setDishId(UUID dishId) {
        this.dishId = dishId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDishType(DishType dishType) {
        this.dishType = dishType;
    }

    public void setTags(FoodTag[] tags) {
        this.tags = tags;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public void setRestaurant(RestaurantJpaEntity restaurant) {
        this.restaurant = restaurant;
    }

    public void setParentId(UUID parentId) {
        this.parentId = parentId;
    }

    public void setPublishAt(LocalDateTime publishAt) {
        this.publishAt = publishAt;
    }

    @Override
    public String toString() {
        return "DishJpaEntity{" +
                "dishId=" + dishId +
                ", parentId=" + parentId +
                ", name='" + name + '\'' +
                ", dishType=" + dishType +
                ", tags=" + Arrays.toString(tags) +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", pictureUrl='" + pictureUrl + '\'' +
                ", state=" + state +
                ", publishAt=" + publishAt +
                ", restaurant=" + restaurant +
                '}';
    }
}
