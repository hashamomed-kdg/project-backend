import { useState, useEffect } from 'react';
import { Dish, DishType, DishTag, DishState } from './types/types';
import { dishApi } from './services/api';
import Header from './components/Header';
import FilterBar from './components/FilterBar';
import DishList from './components/DishList';
import DishForm from './components/DishForm';

function App() {
    const [dishes, setDishes] = useState<Dish[]>([]);
    const [filteredDishes, setFilteredDishes] = useState<Dish[]>([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);
    const [showForm, setShowForm] = useState(false);
    const [editingDish, setEditingDish] = useState<Dish | null>(null);

    // Filter states
    const [selectedType, setSelectedType] = useState<DishType | 'ALL'>('ALL');
    const [selectedTags, setSelectedTags] = useState<DishTag[]>([]);
    const [selectedState, setSelectedState] = useState<DishState | 'ALL'>('ALL');
    const [searchQuery, setSearchQuery] = useState('');

    // Load dishes on mount
    useEffect(() => {
        loadDishes();
    }, []);

    // Apply filters whenever dishes or filter criteria change
    useEffect(() => {
        applyFilters();
    }, [dishes, selectedType, selectedTags, selectedState, searchQuery]);

    const loadDishes = async () => {
        try {
            setLoading(true);
            setError(null);
            const data = await dishApi.getAllDishes();
            setDishes(data);
        } catch (err) {
            setError('Failed to load dishes. Make sure your backend is running on http://localhost:8080');
            console.error(err);
        } finally {
            setLoading(false);
        }
    };

    const applyFilters = () => {
        let filtered = [...dishes];

        // Filter by type
        if (selectedType !== 'ALL') {
            filtered = filtered.filter(dish => dish.dishType === selectedType);
        }

        // Filter by tags
        if (selectedTags.length > 0) {
            filtered = filtered.filter(dish =>
                selectedTags.every(tag => dish.tags.includes(tag))
            );
        }

        // Filter by state
        if (selectedState !== 'ALL') {
            filtered = filtered.filter(dish => dish.state === selectedState);
        }

        // Filter by search query
        if (searchQuery) {
            filtered = filtered.filter(dish =>
                dish.name.toLowerCase().includes(searchQuery.toLowerCase())
            );
        }

        setFilteredDishes(filtered);
    };

    const handleCreateDish = async (dishData: any) => {
        try {
            await dishApi.createDish(dishData);
            await loadDishes();
            setShowForm(false);
            alert('Dish created successfully!');
        } catch (err) {
            alert('Failed to create dish');
            console.error(err);
        }
    };

    const handleEditDish = async (dishData: any) => {
        if (!editingDish) return;
        try {
            await dishApi.editDish(editingDish.dishId, dishData);
            await loadDishes();
            setShowForm(false);
            setEditingDish(null);
            alert('Dish updated successfully!');
        } catch (err) {
            alert('Failed to update dish');
            console.error(err);
        }
    };

    const handleToggleState = async (dish: Dish) => {
        const newState: DishState = dish.state === 'PUBLISHED' ? 'DRAFT' : 'PUBLISHED';
        try {
            await dishApi.changeState(dish.dishId, newState);
            await loadDishes();
            alert(`Dish ${newState === 'PUBLISHED' ? 'published' : 'set to draft'} successfully!`);
        } catch (err) {
            alert('Failed to change dish state');
            console.error(err);
        }
    };

    const handleApplyAllDrafts = async () => {
        if (!confirm('Are you sure you want to publish all draft dishes?')) return;
        try {
            await dishApi.applyAllDrafts();
            await loadDishes();
            alert('All drafts published successfully!');
        } catch (err) {
            alert('Failed to apply all drafts');
            console.error(err);
        }
    };

    const openEditForm = (dish: Dish) => {
        setEditingDish(dish);
        setShowForm(true);
    };

    const closeForm = () => {
        setShowForm(false);
        setEditingDish(null);
    };

    const draftCount = dishes.filter(d => d.state === 'DRAFT').length;

    return (
        <div className="app">
            <Header />

            <div className="container">
                <div className="action-bar">
                    <button className="btn btn-primary" onClick={() => setShowForm(true)}>
                        + Create New Dish
                    </button>
                    {draftCount > 0 && (
                        <button className="btn btn-secondary" onClick={handleApplyAllDrafts}>
                            Publish All Drafts ({draftCount})
                        </button>
                    )}
                </div>

                <FilterBar
                    selectedType={selectedType}
                    onTypeChange={setSelectedType}
                    selectedTags={selectedTags}
                    onTagsChange={setSelectedTags}
                    selectedState={selectedState}
                    onStateChange={setSelectedState}
                    searchQuery={searchQuery}
                    onSearchChange={setSearchQuery}
                />

                {loading && <div className="loading">Loading dishes...</div>}
                {error && <div className="error">{error}</div>}

                {!loading && !error && (
                    <DishList
                        dishes={filteredDishes}
                        onEdit={openEditForm}
                        onToggleState={handleToggleState}
                    />
                )}

                {showForm && (
                    <DishForm
                        dish={editingDish}
                        onSubmit={editingDish ? handleEditDish : handleCreateDish}
                        onCancel={closeForm}
                    />
                )}
            </div>
        </div>
    );
}

export default App;