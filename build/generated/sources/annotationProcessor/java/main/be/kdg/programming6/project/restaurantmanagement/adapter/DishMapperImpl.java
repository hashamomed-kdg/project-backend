package be.kdg.programming6.project.restaurantmanagement.adapter;

import be.kdg.programming6.project.restaurantmanagement.adapter.in.request.CreateDishRequest;
import be.kdg.programming6.project.restaurantmanagement.adapter.in.request.EditDishRequest;
import be.kdg.programming6.project.restaurantmanagement.adapter.in.response.DishDto;
import be.kdg.programming6.project.restaurantmanagement.domain.Dish;
import be.kdg.programming6.project.restaurantmanagement.domain.valueobject.DishState;
import be.kdg.programming6.project.restaurantmanagement.domain.valueobject.DishType;
import be.kdg.programming6.project.restaurantmanagement.domain.valueobject.FoodTag;
import be.kdg.programming6.project.restaurantmanagement.domain.valueobject.PictureUrl;
import be.kdg.programming6.project.restaurantmanagement.port.in.CreateDishCommand;
import be.kdg.programming6.project.restaurantmanagement.port.in.EditDishCommand;
import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-30T12:13:49+0100",
    comments = "version: 1.6.3, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.14.3.jar, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class DishMapperImpl implements DishMapper {

    @Override
    public EditDishCommand toEditCommand(UUID restaurantId, UUID dishId, EditDishRequest request) {
        if ( restaurantId == null && dishId == null && request == null ) {
            return null;
        }

        Set<FoodTag> tags = null;
        String name = null;
        DishType dishType = null;
        String description = null;
        BigDecimal price = null;
        PictureUrl pictureUrl = null;
        if ( request != null ) {
            tags = toFoodTags( request.foodTags() );
            name = request.name();
            dishType = toDishType( request.dishType() );
            description = request.description();
            price = request.price();
            pictureUrl = toPictureUrl( request.pictureUrl() );
        }
        UUID restaurantId1 = null;
        restaurantId1 = restaurantId;
        UUID dishId1 = null;
        dishId1 = dishId;

        EditDishCommand editDishCommand = new EditDishCommand( restaurantId1, dishId1, name, dishType, tags, description, price, pictureUrl );

        return editDishCommand;
    }

    @Override
    public CreateDishCommand toCreateCommand(UUID restaurantId, CreateDishRequest request) {
        if ( restaurantId == null && request == null ) {
            return null;
        }

        Set<FoodTag> tags = null;
        String name = null;
        DishType dishType = null;
        String description = null;
        BigDecimal price = null;
        PictureUrl pictureUrl = null;
        DishState state = null;
        if ( request != null ) {
            tags = toFoodTags( request.foodTags() );
            name = request.name();
            dishType = toDishType( request.dishType() );
            description = request.description();
            price = request.price();
            pictureUrl = toPictureUrl( request.pictureUrl() );
            if ( request.state() != null ) {
                state = Enum.valueOf( DishState.class, request.state() );
            }
        }
        UUID restaurantId1 = null;
        restaurantId1 = restaurantId;

        CreateDishCommand createDishCommand = new CreateDishCommand( name, dishType, tags, description, price, pictureUrl, state, restaurantId1 );

        return createDishCommand;
    }

    @Override
    public DishDto toDto(Dish dish) {
        if ( dish == null ) {
            return null;
        }

        Set<String> foodTags = null;
        UUID dishId = null;
        String name = null;
        String dishType = null;
        String description = null;
        BigDecimal price = null;
        String pictureUrl = null;
        String state = null;

        foodTags = fromFoodTags( dish.getTags() );
        dishId = fromDishId( dish.getDishId() );
        name = dish.getName();
        dishType = fromDishType( dish.getDishType() );
        description = dish.getDescription();
        price = dish.getPrice();
        pictureUrl = fromPictureUrl( dish.getPictureUrl() );
        if ( dish.getState() != null ) {
            state = dish.getState().name();
        }

        DishDto dishDto = new DishDto( dishId, name, dishType, foodTags, description, price, pictureUrl, state );

        return dishDto;
    }
}
