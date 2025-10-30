export type DishType = 'MAIN' | 'STARTER' | 'DESSERT';
export type DishTag = 'GLUTEN_FREE' | 'VEGETARIAN' | 'VEGAN';
export type DishState = 'PUBLISHED' | 'DRAFT';

export interface Dish {
    dishId: string;
    parentId?: string;
    name: string;
    dishType: DishType;
    tags: DishTag[];
    description: string;
    price: number;
    pictureUrl: string;
    state: DishState;
    publishAt?: string;
    restaurantUuid: string;
}

export interface CreateDishRequest {
    name: string;
    dishType: DishType;
    tags: DishTag[];
    description: string;
    price: number;
    pictureUrl: string;
    state: DishState;
}

export interface EditDishRequest extends CreateDishRequest {
    dishId: string;
}

export interface Restaurant {
    uuid: string;
    restaurantName: string;
    cuisineType: string;
}