package be.kdg.programming6.project.restaurantmanagement.domain;

import be.kdg.programming6.project.common.commands.OrderDecisionRequest;
import be.kdg.programming6.project.common.events.DomainEvent;
import be.kdg.programming6.project.common.events.order.AddressDtoForEvent;
import be.kdg.programming6.project.common.events.order.OrderAcceptedEvent;
import be.kdg.programming6.project.common.events.order.OrderReadyForPickupEvent;
import be.kdg.programming6.project.common.events.order.OrderRejectedEvent;
import be.kdg.programming6.project.common.events.restaurant.TimeRangeDtoForEvent;
import be.kdg.programming6.project.common.events.restaurant.RestaurantCreatedEvent;
import be.kdg.programming6.project.common.events.restaurant.RestaurantOpeningsHoursChangedEvent;
import be.kdg.programming6.project.common.events.restaurant.RestaurantStatusChangedEvent;
import be.kdg.programming6.project.restaurantmanagement.adapter.RestaurantMapper;
import be.kdg.programming6.project.restaurantmanagement.domain.valueobject.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class Restaurant {

    private RestaurantId restaurantId;
    private OwnerId owner;
    private String restaurantName;
    private Address address;
    private EmailAddress emailAddress;
    private List<PictureUrl> pictures;
    private CuisineType cuisineType;
    private double defaultPreparationTime;
    private OpeningHours openingHours;
    private RestaurantStatus manualStatus;
    private Menu menu;


    private final List<DomainEvent> domainEvents = new ArrayList<>();

    public Restaurant(RestaurantId restaurantId, OwnerId owner, String restaurantName, Address address, EmailAddress emailAddress, List<PictureUrl> pictures, CuisineType cuisineType, double defaultPreparationTime, OpeningHours openingHours) {
        this.restaurantId = restaurantId;
        this.owner = owner;
        this.restaurantName = restaurantName;
        this.address = address;
        this.emailAddress = emailAddress;
        this.pictures = pictures;
        this.cuisineType = cuisineType;
        this.defaultPreparationTime = defaultPreparationTime;
        this.openingHours = openingHours;
        setManualStatus();
        this.menu = new Menu();

        this.domainEvents.add(new RestaurantCreatedEvent(
                this.owner.uuid(),
                this.restaurantId.uuid(),
                RestaurantMapper.toOpeningHoursForEvent(openingHours),
                manualStatus.name()
        ));
    }


    public boolean isOpen(LocalDateTime time) {
        return openingHours.isOpenAt(time);
    }

    public void changeManualStatus(RestaurantStatus status) {
        this.manualStatus = status;
        this.domainEvents.add(new RestaurantStatusChangedEvent(this.owner.uuid(), this.restaurantId.uuid(), manualStatus.name()));
    }

    private void setManualStatus() {
        if (isOpen(LocalDateTime.now())) {
            this.manualStatus = RestaurantStatus.OPEN;
        } else {
            this.manualStatus = RestaurantStatus.CLOSED;
        }
    }

    public void setManualStatus(RestaurantStatus status) {
        this.manualStatus = status;
    }

    public void updateOpeningHours(OpeningHours openingHours) {
        this.openingHours = openingHours;
        setManualStatus();
        RestaurantMapper.toOpeningHoursForEvent(openingHours);
        this.domainEvents.add(new RestaurantOpeningsHoursChangedEvent(this.owner.uuid(), this.restaurantId.uuid(),
                RestaurantMapper.toOpeningHoursForEvent(openingHours)));
    }

    public void acceptOrder(OrderDecisionRequest request){
        this.domainEvents.add(new OrderAcceptedEvent(
                request.orderId(),
                request.restaurantId(),
                new AddressDtoForEvent(
                        this.address.street(),
                        this.address.number(),
                        this.address.postalCode(),
                        this.address.city()
                ),
                new AddressDtoForEvent(
                        request.deliveryAddress().street(),
                        request.deliveryAddress().number(),
                        request.deliveryAddress().postalCode(),
                        request.deliveryAddress().city()
                )
        ));
    }

    public void rejectOrder(OrderDecisionRequest request, String reason){
        this.domainEvents.add(new OrderRejectedEvent(request.orderId(), request.restaurantId(), reason));
    }

    public void markOrderReadyForPickUp(UUID orderId){
        this.domainEvents.add(new OrderReadyForPickupEvent(orderId, this.restaurantId.uuid()));
    }



    //TODO: replace setters with either constructors or name the setters differently

    public RestaurantId getRestaurantId() {
        return restaurantId;
    }

    public OwnerId getOwner() {
        return owner;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public Address getAddress() {
        return address;
    }

    public EmailAddress getEmailAddress() {
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

    public List<DomainEvent> getDomainEvents() {
        return domainEvents;
    }


    public void setRestaurantId(RestaurantId restaurantId) {
        this.restaurantId = restaurantId;
    }

    public void setOwner(OwnerId owner) {
        this.owner = owner;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setEmailAddress(EmailAddress emailAddress) {
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





    public void setMenu(Menu menu) {
        this.menu = menu;
    }


    public Menu getMenu() {
        return menu;
    }

    public RestaurantStatus getManualStatus() {
        return manualStatus;
    }


    @Override
    public String toString() {
        return "Restaurant{" +
                "restaurantID=" + restaurantId +
                ", owner=" + owner +
                ", restaurantName='" + restaurantName + '\'' +
                ", address=" + address +
                ", emailAddress=" + emailAddress +
                ", pictures=" + pictures +
                ", cuisineType=" + cuisineType +
                ", averagePreparationTime=" + defaultPreparationTime +
                ", openingHours=" + openingHours +
                ", menu=" + menu +
                '}';
    }
}
