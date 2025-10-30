import { Dish } from '../types/types';

interface DishCardProps {
    dish: Dish;
    onEdit: () => void;
}

export default function DishCard({ dish, onEdit }: DishCardProps) {
    const tags = dish.foodTags || [];
    const isDraft = dish.state === 'DRAFT';
    const hasParent = dish.parentId != null;

    return (
        <div className={`dish-card ${dish.state.toLowerCase()}`}>
            <div className="dish-image">
                <img src={dish.pictureUrl || 'https://via.placeholder.com/300x200?text=No+Image'} alt={dish.name} onError={(e) => {
                    e.currentTarget.src = 'https://via.placeholder.com/300x200?text=No+Image';
                }} />
                <span className={`badge badge-${dish.state.toLowerCase()}`}>
          {isDraft ? 'DRAFT' : 'PUBLISHED'}
        </span>
            </div>

            <div className="dish-content">
                <div className="dish-header">
                    <h3>{dish.name}</h3>
                    <span className="dish-type">{dish.dishType}</span>
                </div>

                {isDraft && hasParent && (
                    <p className="draft-info">📝 Draft with unpublished changes</p>
                )}

                <p className="dish-description">{dish.description}</p>

                <div className="dish-tags">
                    {tags.map(tag => (
                        <span key={tag} className="tag">{tag.replace('_', ' ')}</span>
                    ))}
                </div>

                <div className="dish-footer">
                    <span className="dish-price">€{dish.price.toFixed(2)}</span>
                    <div className="dish-actions">
                        <button className="btn-icon" onClick={onEdit} title="Edit">
                            ✏️
                        </button>
                    </div>
                </div>
            </div>
        </div>
    );
}