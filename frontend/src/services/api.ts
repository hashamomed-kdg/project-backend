import { Dish, CreateDishRequest, DishState } from '../types/types';

const API_BASE_URL = '/api';

// Default restaurant ID from your sample data
const DEFAULT_RESTAURANT_ID = '550e8400-e29b-41d4-a716-446655440000';

export const dishApi = {
    // Get all dishes for a restaurant (you'll need to add this endpoint to your backend)
    async getAllDishes(restaurantId: string = DEFAULT_RESTAURANT_ID): Promise<Dish[]> {
        const response = await fetch(`${API_BASE_URL}/restaurants/${restaurantId}/dishes`);
        if (!response.ok) throw new Error('Failed to fetch dishes');
        return response.json();
    },

    // Create a new dish
    async createDish(dish: CreateDishRequest, restaurantId: string = DEFAULT_RESTAURANT_ID): Promise<Dish> {
        const response = await fetch(`${API_BASE_URL}/restaurants/${restaurantId}/menu/createDish`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(dish),
        });
        if (!response.ok) throw new Error('Failed to create dish');
        return response.json();
    },

    // Edit an existing dish
    async editDish(dishId: string, dish: CreateDishRequest, restaurantId: string = DEFAULT_RESTAURANT_ID): Promise<Dish> {
        const response = await fetch(`${API_BASE_URL}/restaurants/${restaurantId}/menu/${dishId}/editDish`, {
            method: 'PATCH',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(dish),
        });
        if (!response.ok) throw new Error('Failed to edit dish');
        return response.json();
    },

    // Change dish state (PUBLISHED/DRAFT)
    async changeState(dishId: string, state: DishState, restaurantId: string = DEFAULT_RESTAURANT_ID): Promise<Dish> {
        const response = await fetch(`${API_BASE_URL}/restaurants/${restaurantId}/menu/${dishId}/changeStateDish`, {
            method: 'PATCH',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ state }),
        });
        if (!response.ok) throw new Error('Failed to change dish state');
        return response.json();
    },

    // Apply all drafts
    async applyAllDrafts(restaurantId: string = DEFAULT_RESTAURANT_ID): Promise<void> {
        const response = await fetch(`${API_BASE_URL}/restaurants/${restaurantId}/menu/applyAllDrafts`, {
            method: 'PATCH',
        });
        if (!response.ok) throw new Error('Failed to apply all drafts');
    },
};