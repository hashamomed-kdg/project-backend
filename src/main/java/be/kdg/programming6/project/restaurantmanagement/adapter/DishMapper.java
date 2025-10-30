package be.kdg.programming6.project.restaurantmanagement.adapter;

import be.kdg.programming6.project.restaurantmanagement.adapter.in.request.CreateDishRequest;
import be.kdg.programming6.project.restaurantmanagement.adapter.in.request.EditDishRequest;
import be.kdg.programming6.project.restaurantmanagement.adapter.in.response.DishDto;
import be.kdg.programming6.project.restaurantmanagement.domain.Dish;
import be.kdg.programming6.project.restaurantmanagement.domain.valueobject.*;
import be.kdg.programming6.project.restaurantmanagement.port.in.CreateDishCommand;
import be.kdg.programming6.project.restaurantmanagement.port.in.EditDishCommand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DishMapper {

    @Mapping(target = "tags", source = "request.foodTags")
    @Mapping(target = "dishId", source = "dishId")
    @Mapping(target = "restaurantId", source = "restaurantId")
    EditDishCommand toEditCommand(UUID restaurantId, UUID dishId, EditDishRequest request);

    @Mapping(target = "tags", source = "request.foodTags")
    @Mapping(target = "restaurantId", source = "restaurantId")
    CreateDishCommand toCreateCommand(UUID restaurantId, CreateDishRequest request);



    @Mapping(target = "foodTags", source = "tags")
    DishDto toDto(Dish dish);


    default DishType toDishType(String dishType) {
        return DishType.valueOf(dishType);
    }
    default String fromDishType(DishType dishType) {
        return dishType.name();
    }

    default PictureUrl toPictureUrl(String pictureUrl) {
        return new PictureUrl(pictureUrl);
    }
    default String fromPictureUrl(PictureUrl pictureUrl) {
        return pictureUrl.url();
    }

    default UUID fromDishId(DishId dishId) {
        return dishId.uuid();
    }
    default DishId toDishId(UUID dishId) {
        return DishId.of(dishId);
    }

    default List<DishId> toDishIds(List<UUID> dishIds) {
        return dishIds.stream()
                .map(DishId::of)
                .collect(Collectors.toList());
    }

    default Set<FoodTag> toFoodTags(Set<String> tags) {
        if (tags == null) {
            return new HashSet<>();
        }
        return tags.stream()
                .map(FoodTag::valueOf)
                .collect(Collectors.toSet());
    }

    default Set<String> fromFoodTags(Set<FoodTag> tags) {
        if (tags == null) {
            return new HashSet<>();
        }
        return tags.stream()
                .map(Enum::name)
                .collect(Collectors.toSet());
    }
}
