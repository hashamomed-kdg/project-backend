package be.kdg.programming6.project.restaurantmanagement.adapter.out.jpa.entity;

import be.kdg.programming6.project.restaurantmanagement.domain.valueobject.CuisineType;
import be.kdg.programming6.project.restaurantmanagement.domain.valueobject.OpeningHours;
import be.kdg.programming6.project.restaurantmanagement.domain.valueobject.PictureUrl;
import be.kdg.programming6.project.restaurantmanagement.domain.valueobject.RestaurantStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;


import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "restaurants")
public class RestaurantJpaEntity {

    @Id
    private UUID uuid;

    @Column(nullable = false)
    private UUID owner;

    @Column(name = "restaurant_name")
    private String restaurantName;

    @Embedded
    private AddressEmbeddable address;


    @Column(name = "email_address")
    private String emailAddress;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private List<PictureUrl> pictures;

    @Column(name = "cuisine_type")
    @Enumerated(EnumType.STRING)
    private CuisineType cuisineType;

    @Column(name = "default_preparation_time")
    private double defaultPreparationTime;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "opening_hours", columnDefinition = "jsonb")
    private OpeningHours openingHours;

    @Enumerated(EnumType.STRING)
    @Column(name = "manual_status")
    private RestaurantStatus manualStatus;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DishJpaEntity> dishes;

    protected RestaurantJpaEntity() {
    }


    public RestaurantJpaEntity(UUID uuid, UUID owner, String restaurantName, AddressEmbeddable address, String emailAddress, List<PictureUrl> pictures, CuisineType cuisineType, double defaultPreparationTime, OpeningHours openingHours) {
        this.uuid = uuid;
        this.owner = owner;
        this.restaurantName = restaurantName;
        this.address = address;
        this.emailAddress = emailAddress;
        this.pictures = pictures;
        this.cuisineType = cuisineType;
        this.defaultPreparationTime = defaultPreparationTime;
        this.openingHours = openingHours;
    }

    public UUID getUuid() {
        return uuid;
    }

    public UUID getOwner() {
        return owner;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public AddressEmbeddable getAddress() {
        return address;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public List<PictureUrl> getPictures() {
        return pictures;
    }

    public CuisineType getCuisineType() {
        return cuisineType;
    }

    public double getDefaultPreparationTime() {
        return defaultPreparationTime;
    }

    public OpeningHours getOpeningHours() {
        return openingHours;
    }

    public RestaurantStatus getManualStatus() {
        return manualStatus;
    }

    public List<DishJpaEntity> getDishes() {
        return dishes;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public void setOwner(UUID owner) {
        this.owner = owner;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public void setAddress(AddressEmbeddable address) {
        this.address = address;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setPictures(List<PictureUrl> pictures) {
        this.pictures = pictures;
    }

    public void setCuisineType(CuisineType cuisineType) {
        this.cuisineType = cuisineType;
    }

    public void setDefaultPreparationTime(double defaultPreparationTime) {
        this.defaultPreparationTime = defaultPreparationTime;
    }

    public void setOpeningHours(OpeningHours openingHours) {
        this.openingHours = openingHours;
    }

    public void setManualStatus(RestaurantStatus manualStatus) {
        this.manualStatus = manualStatus;
    }

    public void setDishes(List<DishJpaEntity> dishes) {
        this.dishes = dishes;
    }

    @Override
    public String toString() {
        return "RestaurantJpaEntity{" +
                "uuid=" + uuid +
                ", owner=" + owner +
                ", restaurantName='" + restaurantName + '\'' +
                ", address=" + address +
                ", emailAddress='" + emailAddress + '\'' +
                ", pictures=" + pictures +
                ", cuisineType=" + cuisineType +
                ", defaultPreparationTime=" + defaultPreparationTime +
                ", openingHours=" + openingHours +
                ", dishes=" + dishes +
                '}';
    }
}
