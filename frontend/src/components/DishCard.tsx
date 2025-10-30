import { Dish } from '../types/types';

interface DishCardProps {
    dish: Dish;
    onEdit: () => void;
    onToggleState: () => void;
}

export default function DishCard({ dish, onEdit, onToggleState }: DishCardProps) {
    return (
        <div className={`dish-card ${dish.state.toLowerCase()}`}>
            <div className="dish-image">
                <img src={dish.pictureUrl} alt={dish.name} onError={(e) => {
                    e.currentTarget.src = 'https://via.placeholder.com/300x200?text=No+Image';
                }} />
                <span className={`badge badge-${dish.state.toLowerCase()}`}>
          {dish.state}
        </span>
            </div>

            <div className="dish-content">
                <div className="dish-header">
                    <h3>{dish.name}</h3>
                    <span className="dish-type">{dish.dishType}</span>
                </div>

                <p className="dish-description">{dish.description}</p>

                <div className="dish-tags">
                    {dish.tags.map(tag => (
                        <span key={tag} className="tag">{tag.replace('_', ' ')}</span>
                    ))}
                </div>

                <div className="dish-footer">
                    <span className="dish-price">€{dish.price.toFixed(2)}</span>
                    <div className="dish-actions">
                        <button className="btn-icon" onClick={onEdit} title="Edit">
                            ✏️
                        </button>
                        <button className="btn-icon" onClick={onToggleState} title={`Set to ${dish.state === 'PUBLISHED' ? 'Draft' : 'Published'}`}>
                            {dish.state === 'PUBLISHED' ? '📝' : '✅'}
                        </button>
                    </div>
                </div>
            </div>
        </div>
    );
}