import { Dish } from '../types/types';
import DishCard from './DishCard';

interface DishListProps {
    dishes: Dish[];
    onEdit: (dish: Dish) => void;
}

export default function DishList({ dishes, onEdit }: DishListProps) {
    if (dishes.length === 0) {
        return <div className="no-dishes">No dishes found. Try adjusting your filters or create a new dish!</div>;
    }

    return (
        <div className="dish-grid">
            {dishes.map(dish => (
                <DishCard
                    key={dish.dishId}
                    dish={dish}
                    onEdit={() => onEdit(dish)}
                />
            ))}
        </div>
    );
}