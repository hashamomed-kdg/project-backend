package be.kdg.programming6.project.restaurantmanagement.adapter;

import be.kdg.programming6.project.common.events.restaurant.TimeRangeDtoForEvent;
import be.kdg.programming6.project.restaurantmanagement.adapter.in.request.CreateRestaurantRequest;
import be.kdg.programming6.project.restaurantmanagement.adapter.in.request.dtos.AddressDto;
import be.kdg.programming6.project.restaurantmanagement.adapter.in.request.dtos.OpeningHoursDto;
import be.kdg.programming6.project.restaurantmanagement.adapter.in.request.dtos.TimeRangeDto;
import be.kdg.programming6.project.restaurantmanagement.adapter.in.response.RestaurantDto;
import be.kdg.programming6.project.restaurantmanagement.adapter.out.jpa.entity.AddressEmbeddable;
import be.kdg.programming6.project.restaurantmanagement.adapter.out.jpa.entity.DishJpaEntity;
import be.kdg.programming6.project.restaurantmanagement.adapter.out.jpa.entity.RestaurantJpaEntity;
import be.kdg.programming6.project.restaurantmanagement.domain.Dish;
import be.kdg.programming6.project.restaurantmanagement.domain.Menu;
import be.kdg.programming6.project.restaurantmanagement.domain.Restaurant;
import be.kdg.programming6.project.restaurantmanagement.domain.valueobject.*;
import be.kdg.programming6.project.restaurantmanagement.port.in.CreateRestaurantCommand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RestaurantMapper {

    @Mapping(source = "owner", target = "owner", qualifiedByName = "uuidToOwnerId")
    @Mapping(source = "name", target = "restaurantName")
    @Mapping(source = "email", target = "emailAddress", qualifiedByName = "stringToEmailAddress")
    @Mapping(source = "cuisineType", target = "cuisineType", qualifiedByName = "stringToCuisineType")
    @Mapping(source = "pictures", target = "pictures", qualifiedByName = "stringsToPictureUrls")
    @Mapping(source = "address", target = "address", qualifiedByName = "addressDtoToAddress")
    @Mapping(source = "openingHours", target = "openingHours", qualifiedByName = "openingHoursDtoToOpeningHours")
    CreateRestaurantCommand toCommand(CreateRestaurantRequest request);

    @Mapping(source = "restaurantId", target = "restaurantId", qualifiedByName = "restaurantIdToUuid")
    @Mapping(source = "owner", target = "owner", qualifiedByName = "ownerIdToUuid")
    @Mapping(source = "restaurantName", target = "name")
    @Mapping(source = "emailAddress", target = "email", qualifiedByName = "emailAddressToString")
    @Mapping(source = "cuisineType", target = "cuisineType", qualifiedByName = "cuisineTypeToString")
    @Mapping(source = "pictures", target = "pictures", qualifiedByName = "pictureUrlsToStrings")
    @Mapping(source = "address", target = "address", qualifiedByName = "addressToAddressDto")
    @Mapping(source = "openingHours", target = "openingHours", qualifiedByName = "openingHoursToOpeningHoursDto")
    @Mapping(source = "manualStatus", target = "manualStatus", qualifiedByName = "manualStatusToString")
    RestaurantDto toDto(Restaurant restaurant);


    @Mapping(source = "restaurantId", target = "uuid", qualifiedByName = "restaurantIdToUuid")
    @Mapping(source = "owner", target = "owner", qualifiedByName = "ownerIdToUuid")
    @Mapping(source = "emailAddress", target = "emailAddress", qualifiedByName = "emailAddressToString")
    @Mapping(source = "cuisineType", target = "cuisineType", qualifiedByName = "cuisineTypeToString")
    @Mapping(source = "address", target = "address", qualifiedByName = "addressToAddressEmbeddable")
    @Mapping(source = "menu", target = "dishes", qualifiedByName = "menuToDishJpaList")
    @Mapping(source = "manualStatus", target = "manualStatus", qualifiedByName = "manualStatusToString")
    RestaurantJpaEntity toEntity(Restaurant restaurant);

    @Mapping(source = "uuid", target = "restaurantId", qualifiedByName = "uuidToRestaurantId")
    @Mapping(source = "owner", target = "owner", qualifiedByName = "uuidToOwnerId")
    @Mapping(source = "emailAddress", target = "emailAddress", qualifiedByName = "stringToEmailAddress")
    @Mapping(source = "address", target = "address", qualifiedByName = "addressEmbeddableToAddress")
    @Mapping(source = "dishes", target = "menu", qualifiedByName = "dishJpaListToMenu")
    @Mapping(source = "manualStatus", target = "manualStatus", qualifiedByName = "stringToManualStatus")
    Restaurant toDomain(RestaurantJpaEntity entity);

    @Named("openingHoursDtoToOpeningHours")
    default OpeningHours openingHoursDtoToOpeningHours(OpeningHoursDto dto) {
        if (dto == null || dto.openingHours() == null) {
            return new OpeningHours(Map.of());
        }

        Map<DayOfWeek, List<TimeRange>> mapped = dto.openingHours().entrySet().stream()
                .collect(Collectors.toMap(
                        e -> DayOfWeek.valueOf(e.getKey()),
                        e -> {
                            List<TimeRangeDto> timeRanges = e.getValue();
                            if (timeRanges == null || timeRanges.isEmpty()) {
                                return null;
                            }

                            return timeRanges.stream().map(tr -> new TimeRange(
                                    LocalTime.parse(tr.open()),
                                    LocalTime.parse(tr.close())
                            )).toList();
                        }
                ));
        return new OpeningHours(mapped);
    }

    @Named("openingHoursToOpeningHoursDto")
    default OpeningHoursDto openingHoursToOpeningHoursDto(OpeningHours openingHours) {
        if (openingHours == null || openingHours.openingHours() == null) {
            return new OpeningHoursDto(Map.of());
        }

        Map<String, List<TimeRangeDto>> mapped = openingHours.openingHours().entrySet().stream()
                .filter(e -> e.getValue() != null)
                .collect(Collectors.toMap(
                        e -> e.getKey().name(),
                        e -> {
                            List<TimeRange> timeRanges = e.getValue();
                            return timeRanges.stream().map(tr -> new TimeRangeDto(
                                    tr.open().toString(),
                                    tr.close().toString()
                            )).toList();
                        }
                ));
        return new OpeningHoursDto(mapped);
    }

    static Map<String, List<TimeRangeDtoForEvent>> toOpeningHoursForEvent(OpeningHours openingHours){
        Map<String, List<TimeRangeDtoForEvent>>  mapped = openingHours.openingHours().entrySet().stream()
                .filter(e -> e.getValue() != null)
                .collect(Collectors.toMap(
                        e -> e.getKey().name(),
                        e -> {
                            List<TimeRange> timeRanges = e.getValue();
                            return timeRanges.stream().map(tr -> new TimeRangeDtoForEvent(
                                    tr.open().toString(),
                                    tr.close().toString()
                            )).toList();
                        }
                ));
        return mapped;
    }


    @Named("uuidToRestaurantId")
    default RestaurantId uuidToRestaurantId(UUID uuid) {
        return RestaurantId.of(uuid);
    }
    @Named("restaurantIdToUuid")
    default UUID restaurantIdToUuid(RestaurantId restaurantId) {
        return restaurantId.uuid();
    }

    @Named("uuidToOwnerId")
    default OwnerId uuidToOwnerId(UUID uuid) {
        return OwnerId.of(uuid);
    }

    @Named("ownerIdToUuid")
    default UUID ownerIdToUuid (OwnerId ownerId){
        return ownerId.uuid();
    }

    @Named("stringToEmailAddress")
    default EmailAddress stringToEmailAddress(String email) {
        return new EmailAddress(email);
    }

    @Named("emailAddressToString")
    default String emailAddressToString(EmailAddress emailAddress) {
        return emailAddress.email();
    }

    @Named("cuisineTypeToString")
    default String cuisineTypeToString(CuisineType cuisineType) {
        return cuisineType.name();
    }

    @Named("stringToCuisineType")
    default CuisineType stringToCuisineType(String cuisineType) {
        return CuisineType.valueOf(cuisineType.toUpperCase());
    }
    @Named("stringsToPictureUrls")
    default List<PictureUrl> stringsToPictureUrls(List<String> urls) {
        return urls.stream().map(PictureUrl::new).toList();
    }
    @Named("pictureUrlsToStrings")
    default List<String> pictureUrlsToStrings(List<PictureUrl> urls) {
        return urls.stream().map(PictureUrl::url).toList();
    }

    @Named("stringToManualStatus")
    default RestaurantStatus manualStatusToRestaurantStatus(String status) {
        return RestaurantStatus.valueOf(status.toUpperCase());
    }

    @Named("manualStatusToString")
    default String restaurantStatusToManualStatus(RestaurantStatus status) {
        return status.name();
    }

    @Named("addressDtoToAddress")
    default Address addressDtoToAddress(AddressDto addressDto) {
        return new Address(
                addressDto.street(),
                addressDto.number(),
                addressDto.postalCode(),
                addressDto.city(),
                addressDto.country());
    }

    @Named("addressToAddressDto")
    default AddressDto addressToAddressDto(Address address) {
        return new AddressDto(
                address.street(),
                address.number(),
                address.postalCode(),
                address.city(),
                address.country());
    }

    @Named("addressEmbeddableToAddress")
    default Address addressEmbeddableToAddress(AddressEmbeddable addressEmbeddable){
        return new Address(
                addressEmbeddable.street(),
                addressEmbeddable.number(),
                addressEmbeddable.postalCode(),
                addressEmbeddable.city(),
                addressEmbeddable.country()
        );
    }

    @Named("addressToAddressEmbeddable")
    default AddressEmbeddable addressToAddressEmbeddable(Address address){
        return new AddressEmbeddable(
                address.street(),
                address.number(),
                address.postalCode(),
                address.city(),
                address.country()
        );
    }

    @Named("menuToDishJpaList")
    default List<DishJpaEntity> menuToDishJpaList (Menu menu){
        List<Dish> dishList = menu.getDishes();
        List<DishJpaEntity> dishJpaList = dishList.stream().map(
            domain -> new DishJpaEntity(
                    domain.getDishId().uuid(),
                    domain.getParentId().isPresent() ? domain.getParentId().get().uuid() : null,
                    domain.getName(),
                    domain.getDishType(),
                    domain.getTags().stream().map(Object::toString).map(FoodTag::valueOf).toArray(FoodTag[]::new),
                    domain.getDescription(),
                    domain.getPrice(),
                    domain.getPictureUrl().url(),
                    domain.getState(),
                    domain.getPublishAt().orElse(null)
            )
        ).toList();
        return dishJpaList;
    }

    @Named("dishJpaListToMenu")
    default Menu dishJpaListToMenu (List<DishJpaEntity> dishes) {
        List<Dish> dishList = dishes.stream().map(
            entity -> new Dish(
                    DishId.of(entity.getDishId()),
                    DishId.of(entity.getParentId()),
                    entity.getName(),
                    entity.getDishType(),
                    Arrays.stream(entity.getTags())
                            .collect(Collectors.toSet()),
                    entity.getDescription(),
                    entity.getPrice(),
                    new PictureUrl(entity.getPictureUrl()),
                    entity.getState(),
                    entity.getPublishAt()
            )
        ).toList();
        Menu menu = new Menu();
        dishList.forEach(menu::addDish);
        return menu;
    }

}
