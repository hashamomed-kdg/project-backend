import { useState, useEffect } from 'react';
import { Dish, DishType, DishTag } from '../types/types';

interface DishFormProps {
    dish: Dish | null;
    onSubmit: (data: any) => void;
    onCancel: () => void;
}

export default function DishForm({ dish, onSubmit, onCancel }: DishFormProps) {
    const isEditing = dish !== null;

    const allDishTypes: DishType[] = ['MAIN', 'STARTER', 'DESSERT'];
    const allTags: DishTag[] = ['GLUTEN_FREE', 'VEGETARIAN', 'VEGAN'];

    const [formData, setFormData] = useState({
        name: '',
        description: '',
        price: 0,
        dishType: 'MAIN' as DishType,
        foodTags: [] as DishTag[],
        pictureUrl: '',
        state: isEditing ? 'DRAFT' : 'PUBLISHED', // Always PUBLISHED for new, DRAFT for edits
    });

    useEffect(() => {
        if (dish) {
            setFormData({
                name: dish.name,
                description: dish.description,
                price: dish.price,
                dishType: dish.dishType,
                foodTags: dish.foodTags || [],
                pictureUrl: dish.pictureUrl,
                state: 'DRAFT', // Always DRAFT when editing
            });
        }
    }, [dish]);

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        onSubmit(formData);
    };

    const toggleTag = (tag: DishTag) => {
        setFormData(prev => ({
            ...prev,
            foodTags: prev.foodTags.includes(tag)
                ? prev.foodTags.filter(t => t !== tag)
                : [...prev.foodTags, tag]
        }));
    };

    return (
        <div className="modal-overlay" onClick={onCancel}>
            <div className="modal-content" onClick={(e) => e.stopPropagation()}>
                <div className="modal-header">
                    <h2>{isEditing ? 'Edit Dish' : 'Create New Dish'}</h2>
                    <button className="close-btn" onClick={onCancel}>×</button>
                </div>

                {isEditing && (
                    <div className="info-banner">
                        <strong>ℹ️ Editing Mode:</strong> Changes will be saved as a draft.
                        Use "Publish All Drafts" to apply changes to your menu.
                    </div>
                )}

                <form onSubmit={handleSubmit} className="dish-form">
                    <div className="form-group">
                        <label>Name *</label>
                        <input
                            type="text"
                            required
                            value={formData.name}
                            onChange={(e) => setFormData({ ...formData, name: e.target.value })}
                        />
                    </div>

                    <div className="form-group">
                        <label>Description *</label>
                        <textarea
                            required
                            rows={3}
                            value={formData.description}
                            onChange={(e) => setFormData({ ...formData, description: e.target.value })}
                        />
                    </div>

                    <div className="form-row">
                        <div className="form-group">
                            <label>Price (€) *</label>
                            <input
                                type="number"
                                required
                                min="0"
                                step="0.01"
                                value={formData.price}
                                onChange={(e) => setFormData({ ...formData, price: parseFloat(e.target.value) })}
                            />
                        </div>

                        <div className="form-group">
                            <label>Type *</label>
                            <select
                                value={formData.dishType}
                                onChange={(e) => setFormData({ ...formData, dishType: e.target.value as DishType })}
                            >
                                {allDishTypes.map(type => (
                                    <option key={type} value={type}>
                                        {type}
                                    </option>
                                ))}
                            </select>
                        </div>
                    </div>

                    <div className="form-group">
                        <label>Picture URL</label>
                        <input
                            type="url"
                            value={formData.pictureUrl}
                            onChange={(e) => setFormData({ ...formData, pictureUrl: e.target.value })}
                            placeholder="https://example.com/image.jpg"
                        />
                    </div>

                    <div className="form-group">
                        <label>Tags</label>
                        <div className="checkbox-group">
                            {allTags.map(tag => (
                                <label key={tag} className="checkbox-label">
                                    <input
                                        type="checkbox"
                                        checked={formData.foodTags.includes(tag)}
                                        onChange={() => toggleTag(tag)}
                                    />
                                    {tag.replace('_', ' ')}
                                </label>
                            ))}
                        </div>
                    </div>

                    <div className="form-actions">
                        <button type="button" className="btn btn-secondary" onClick={onCancel}>
                            Cancel
                        </button>
                        <button type="submit" className="btn btn-primary">
                            {isEditing ? 'Save as Draft' : 'Create Dish'}
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );
}