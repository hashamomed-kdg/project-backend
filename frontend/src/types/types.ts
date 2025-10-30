export type DishType = 'MAIN' | 'STARTER' | 'DESSERT';
export type DishTag = 'GLUTEN_FREE' | 'VEGETARIAN' | 'VEGAN';
export type DishState = 'PUBLISHED' | 'DRAFT';

export interface Dish {
    dishId: string;
    parentId?: string;
    name: string;
    dishType: DishType;
    foodTags: DishTag[];
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
    foodTags: DishTag[];
    description: string;
    price: number;
    pictureUrl: string;
    state: DishState;
}

export interface Owner {
    ownerId: string;
    email: string;
    fullName: string;
}

export interface AuthResponse {
    ownerId: string;
    email: string;
    fullName: string;
    restaurantId: string | null;
}

export interface LoginRequest {
    email: string;
    password: string;
}

export interface SignupRequest {
    email: string;
    password: string;
    fullName: string;
}

export interface EditDishRequest extends CreateDishRequest {
    dishId: string;
}

export interface Restaurant {
    uuid: string;
    restaurantName: string;
    cuisineType: string;
}

export interface TimeRangeDto {
    open: string;  // e.g., "09:00"
    close: string; // e.g., "22:00"
}

export interface OpeningHoursDto {
    openingHours: {
        [key: string]: TimeRangeDto[];  // e.g., "MONDAY": [{ open: "09:00", close: "22:00" }]
    };
}

export interface CreateRestaurantRequest {
    owner: string;
    name: string;
    address: {
        street: string;
        number: string;
        postalCode: string;
        city: string;
    };
    email: string;
    cuisineType: string;
    pictures: string[];
    defaultPreparationTime: number;
    openingHours: OpeningHoursDto;
}