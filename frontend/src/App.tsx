import { useState, useEffect } from 'react';
import { Dish, DishType, DishTag, DishState, AuthResponse } from './types/types';
import { dishApi } from './services/api';
import { authApi } from './services/authApi';
import Header from './components/Header';
import FilterBar from './components/FilterBar';
import DishList from './components/DishList';
import DishForm from './components/DishForm';
import LoginForm from './components/LoginForm';
import SignupForm from './components/SignupForm';
import CreateRestaurantForm from './components/CreateRestaurantForm';

type AuthView = 'login' | 'signup';

function App() {
    const [user, setUser] = useState<AuthResponse | null>(null);
    const [authView, setAuthView] = useState<AuthView>('login');
    const [authError, setAuthError] = useState<string | null>(null);

    const [dishes, setDishes] = useState<Dish[]>([]);
    const [filteredDishes, setFilteredDishes] = useState<Dish[]>([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);
    const [showForm, setShowForm] = useState(false);
    const [editingDish, setEditingDish] = useState<Dish | null>(null);

    // Filter states
    const [selectedType, setSelectedType] = useState<DishType | 'ALL'>('ALL');
    const [selectedTags, setSelectedTags] = useState<DishTag[]>([]);
    const [selectedState, setSelectedState] = useState<DishState | 'ALL'>('ALL');
    const [searchQuery, setSearchQuery] = useState('');

    // Check for existing session
    useEffect(() => {
        const savedUser = localStorage.getItem('user');
        if (savedUser) {
            setUser(JSON.parse(savedUser));
        }
    }, []);

    // Load dishes when user has a restaurant
    useEffect(() => {
        if (user?.restaurantId) {
            loadDishes(user.restaurantId);
        }
    }, [user]);

    // Apply filters
    useEffect(() => {
        applyFilters();
    }, [dishes, selectedType, selectedTags, selectedState, searchQuery]);

    const handleLogin = async (data: any) => {
        try {
            setAuthError(null);
            const response = await authApi.login(data);
            setUser(response);
            localStorage.setItem('user', JSON.stringify(response));
        } catch (err: any) {
            setAuthError(err.message);
        }
    };

    const handleSignup = async (data: any) => {
        try {
            setAuthError(null);
            const response = await authApi.signup(data);
            setUser(response);
            localStorage.setItem('user', JSON.stringify(response));
        } catch (err: any) {
            setAuthError(err.message);
        }
    };

    const handleCreateRestaurant = async (data: any) => {
        try {
            setError(null);
            const restaurantData = {
                ...data,
                owner: user?.ownerId,
            };

            console.log('Creating restaurant with data:', restaurantData);

            const response = await fetch('/api/restaurants', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(restaurantData),
            });

            if (!response.ok) {
                const errorText = await response.text();
                throw new Error(errorText || 'Failed to create restaurant');
            }

            const restaurant = await response.json();
            console.log('Restaurant created:', restaurant);

            const updatedUser = { ...user!, restaurantId: restaurant.uuid };
            localStorage.setItem('user', JSON.stringify(updatedUser));
            setUser(updatedUser);

            alert('Restaurant created successfully! Redirecting to dashboard...');

            if (restaurant.uuid) {
                await loadDishes(restaurant.uuid);
            }
        } catch (err: any) {
            console.error('Error creating restaurant:', err);
            setError(err.message);
            alert('Failed to create restaurant: ' + err.message);
        }
    };

    const handleLogout = () => {
        setUser(null);
        localStorage.removeItem('user');
        setDishes([]);
        setAuthView('login');
    };

    const loadDishes = async (restaurantId: string) => {
        try {
            setLoading(true);
            setError(null);
            const data = await dishApi.getAllDishes(restaurantId);
            setDishes(data);
        } catch (err) {
            setError('Failed to load dishes');
            console.error(err);
        } finally {
            setLoading(false);
        }
    };

    const applyFilters = () => {
        let filtered = [...dishes];

        if (selectedType !== 'ALL') {
            filtered = filtered.filter(dish => dish.dishType === selectedType);
        }

        if (selectedTags.length > 0) {
            filtered = filtered.filter(dish =>
                selectedTags.every(tag => (dish.foodTags || []).includes(tag))
            );
        }

        if (selectedState !== 'ALL') {
            filtered = filtered.filter(dish => dish.state === selectedState);
        }

        if (searchQuery) {
            filtered = filtered.filter(dish =>
                dish.name.toLowerCase().includes(searchQuery.toLowerCase())
            );
        }

        setFilteredDishes(filtered);
    };

    const handleCreateDish = async (dishData: any) => {
        try {
            await dishApi.createDish(dishData, user!.restaurantId!);
            await loadDishes(user!.restaurantId!);
            setShowForm(false);
            alert('Dish created and published successfully!');
        } catch (err) {
            alert('Failed to create dish');
            console.error(err);
        }
    };

    const handleEditDish = async (dishData: any) => {
        if (!editingDish) return;
        try {
            await dishApi.editDish(editingDish.dishId, dishData, user!.restaurantId!);
            await loadDishes(user!.restaurantId!);
            setShowForm(false);
            setEditingDish(null);
            alert('Changes saved as draft! Use "Publish All Drafts" to apply changes.');
        } catch (err) {
            alert('Failed to update dish');
            console.error(err);
        }
    };

    const handleApplyAllDrafts = async () => {
        if (!confirm('Are you sure you want to publish all draft changes?')) return;
        try {
            await dishApi.applyAllDrafts(user!.restaurantId!);
            await loadDishes(user!.restaurantId!);
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

    // Not logged in - show auth forms
    if (!user) {
        return (
            <>
                <Header />
                {authView === 'login' ? (
                    <LoginForm
                        onLogin={handleLogin}
                        onSwitchToSignup={() => {
                            setAuthView('signup');
                            setAuthError(null);
                        }}
                        error={authError}
                    />
                ) : (
                    <SignupForm
                        onSignup={handleSignup}
                        onSwitchToLogin={() => {
                            setAuthView('login');
                            setAuthError(null);
                        }}
                        error={authError}
                    />
                )}
            </>
        );
    }

    // Logged in but no restaurant - show create restaurant form
    if (!user.restaurantId) {
        return (
            <>
                <Header />
                <CreateRestaurantForm
                    onCreateRestaurant={handleCreateRestaurant}
                    onLogout={handleLogout}
                />
            </>
        );
    }

    // Logged in with restaurant - show dashboard
    const draftCount = dishes.filter(d => d.state === 'DRAFT' && d.parentId).length;

    return (
        <div className="app">
            <Header />

            <div className="container">
                <div className="dashboard-header">
                    <div>
                        <h2>Welcome, {user.fullName}!</h2>
                        <p>Managing your restaurant dishes</p>
                    </div>
                    <button className="btn btn-secondary" onClick={handleLogout}>
                        Logout
                    </button>
                </div>

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