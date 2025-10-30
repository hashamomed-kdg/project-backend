import { useState } from 'react';

interface CreateRestaurantFormProps {
    onCreateRestaurant: (data: any) => void;
    onLogout: () => void;
}

interface TimeRange {
    open: string;
    close: string;
}

interface DaySchedule {
    enabled: boolean;
    timeRanges: TimeRange[];
}

type WeekSchedule = {
    [key: string]: DaySchedule;
};

export default function CreateRestaurantForm({ onCreateRestaurant, onLogout }: CreateRestaurantFormProps) {
    const [formData, setFormData] = useState({
        restaurantName: '',
        street: '',
        number: '',
        postalCode: '',
        city: '',
        emailAddress: '',
        cuisineType: 'ITALIAN',
        defaultPreparationTime: 30,
        pictureUrls: '',
    });

    const [schedule, setSchedule] = useState<WeekSchedule>({
        MONDAY: { enabled: true, timeRanges: [{ open: '09:00', close: '22:00' }] },
        TUESDAY: { enabled: true, timeRanges: [{ open: '09:00', close: '22:00' }] },
        WEDNESDAY: { enabled: true, timeRanges: [{ open: '09:00', close: '22:00' }] },
        THURSDAY: { enabled: true, timeRanges: [{ open: '09:00', close: '22:00' }] },
        FRIDAY: { enabled: true, timeRanges: [{ open: '09:00', close: '23:00' }] },
        SATURDAY: { enabled: true, timeRanges: [{ open: '09:00', close: '23:00' }] },
        SUNDAY: { enabled: true, timeRanges: [{ open: '10:00', close: '21:00' }] },
    });

    const toggleDay = (day: string, enabled: boolean) => {
        setSchedule(prev => ({
            ...prev,
            [day]: { ...prev[day], enabled }
        }));
    };

    const addTimeRange = (day: string) => {
        setSchedule(prev => ({
            ...prev,
            [day]: {
                ...prev[day],
                timeRanges: [...prev[day].timeRanges, { open: '09:00', close: '17:00' }]
            }
        }));
    };

    const removeTimeRange = (day: string, index: number) => {
        setSchedule(prev => ({
            ...prev,
            [day]: {
                ...prev[day],
                timeRanges: prev[day].timeRanges.filter((_, i) => i !== index)
            }
        }));
    };

    const updateTimeRange = (day: string, index: number, field: 'open' | 'close', value: string) => {
        setSchedule(prev => ({
            ...prev,
            [day]: {
                ...prev[day],
                timeRanges: prev[day].timeRanges.map((tr, i) =>
                    i === index ? { ...tr, [field]: value } : tr
                )
            }
        }));
    };

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();

        // Transform schedule to backend format
        const openingHoursMap: { [key: string]: Array<{ open: string; close: string }> } = {};

        Object.entries(schedule).forEach(([day, daySchedule]) => {
            if (daySchedule.enabled && daySchedule.timeRanges.length > 0) {
                openingHoursMap[day] = daySchedule.timeRanges;
            }
        });

        // Parse picture URLs
        const pictureUrlsList = formData.pictureUrls
            .split(',')
            .map(url => url.trim())
            .filter(url => url.length > 0);

        // Transform to match backend CreateRestaurantRequest structure
        const requestData = {
            owner: null,
            name: formData.restaurantName,
            address: {
                street: formData.street,
                number: formData.number,
                postalCode: formData.postalCode,
                city: formData.city,
            },
            email: formData.emailAddress,
            cuisineType: formData.cuisineType,
            pictures: pictureUrlsList,
            defaultPreparationTime: formData.defaultPreparationTime,
            openingHours: openingHoursMap
        };

        console.log('Submitting restaurant data:', JSON.stringify(requestData, null, 2));
        onCreateRestaurant(requestData);
    };

    const days = ['MONDAY', 'TUESDAY', 'WEDNESDAY', 'THURSDAY', 'FRIDAY', 'SATURDAY', 'SUNDAY'];

    return (
        <div className="auth-container">
            <div className="auth-card extra-large">
                <div className="auth-header">
                    <h2>Create Your Restaurant</h2>
                    <button type="button" onClick={onLogout} className="btn btn-secondary-small">
                        Logout
                    </button>
                </div>

                <p className="welcome-text">Welcome! Let's set up your restaurant.</p>

                <form onSubmit={handleSubmit} className="auth-form">
                    {/* Basic Information */}
                    <h3 className="section-title">Basic Information</h3>

                    <div className="form-group">
                        <label>Restaurant Name *</label>
                        <input
                            type="text"
                            required
                            value={formData.restaurantName}
                            onChange={(e) => setFormData({ ...formData, restaurantName: e.target.value })}
                            placeholder="The Golden Spoon"
                        />
                    </div>

                    {/* Address */}
                    <h3 className="section-title">Address</h3>

                    <div className="form-row">
                        <div className="form-group">
                            <label>Street *</label>
                            <input
                                type="text"
                                required
                                value={formData.street}
                                onChange={(e) => setFormData({ ...formData, street: e.target.value })}
                                placeholder="Main Street"
                            />
                        </div>

                        <div className="form-group form-group-small">
                            <label>Number *</label>
                            <input
                                type="text"
                                required
                                value={formData.number}
                                onChange={(e) => setFormData({ ...formData, number: e.target.value })}
                                placeholder="123"
                            />
                        </div>
                    </div>

                    <div className="form-row">
                        <div className="form-group">
                            <label>Postal Code *</label>
                            <input
                                type="text"
                                required
                                value={formData.postalCode}
                                onChange={(e) => setFormData({ ...formData, postalCode: e.target.value })}
                                placeholder="2000"
                            />
                        </div>

                        <div className="form-group">
                            <label>City *</label>
                            <input
                                type="text"
                                required
                                value={formData.city}
                                onChange={(e) => setFormData({ ...formData, city: e.target.value })}
                                placeholder="Antwerp"
                            />
                        </div>
                    </div>

                    {/* Contact & Details */}
                    <h3 className="section-title">Contact & Details</h3>

                    <div className="form-group">
                        <label>Email Address *</label>
                        <input
                            type="email"
                            required
                            value={formData.emailAddress}
                            onChange={(e) => setFormData({ ...formData, emailAddress: e.target.value })}
                            placeholder="contact@restaurant.com"
                        />
                    </div>

                    <div className="form-row">
                        <div className="form-group">
                            <label>Cuisine Type *</label>
                            <select
                                value={formData.cuisineType}
                                onChange={(e) => setFormData({ ...formData, cuisineType: e.target.value })}
                            >
                                <option value="ITALIAN">Italian</option>
                                <option value="FRENCH">French</option>
                                <option value="JAPANESE">Japanese</option>
                                <option value="CHINESE">Chinese</option>
                                <option value="INDIAN">Indian</option>
                                <option value="MEXICAN">Mexican</option>
                                <option value="OTHER">Other</option>
                            </select>
                        </div>

                        <div className="form-group">
                            <label>Default Preparation Time (minutes) *</label>
                            <input
                                type="number"
                                required
                                min="10"
                                max="120"
                                value={formData.defaultPreparationTime}
                                onChange={(e) => setFormData({ ...formData, defaultPreparationTime: parseInt(e.target.value) })}
                            />
                        </div>
                    </div>

                    <div className="form-group">
                        <label>Picture URLs (optional, comma-separated)</label>
                        <textarea
                            rows={3}
                            value={formData.pictureUrls}
                            onChange={(e) => setFormData({ ...formData, pictureUrls: e.target.value })}
                            placeholder="https://example.com/image1.jpg, https://example.com/image2.jpg"
                        />
                        <small className="help-text-inline">Enter multiple URLs separated by commas</small>
                    </div>

                    {/* Opening Hours */}
                    <h3 className="section-title">Opening Hours</h3>
                    <p className="help-text-inline" style={{ marginBottom: '1rem' }}>
                        You can add multiple time ranges per day (e.g., lunch: 11:00-14:00, dinner: 18:00-22:00)
                    </p>

                    <div className="opening-hours-section">
                        {days.map(day => (
                            <div key={day} className="day-schedule-multi">
                                <div className="day-header-multi">
                                    <label className="checkbox-label">
                                        <input
                                            type="checkbox"
                                            checked={schedule[day].enabled}
                                            onChange={(e) => toggleDay(day, e.target.checked)}
                                        />
                                        <span className="day-name">{day}</span>
                                    </label>

                                    {schedule[day].enabled && (
                                        <button
                                            type="button"
                                            onClick={() => addTimeRange(day)}
                                            className="btn-add-range"
                                            title="Add time range"
                                        >
                                            + Add Range
                                        </button>
                                    )}
                                </div>

                                {schedule[day].enabled ? (
                                    <div className="time-ranges-list">
                                        {schedule[day].timeRanges.map((range, index) => (
                                            <div key={index} className="time-range-row">
                                                <div className="time-inputs">
                                                    <input
                                                        type="time"
                                                        value={range.open}
                                                        onChange={(e) => updateTimeRange(day, index, 'open', e.target.value)}
                                                        className="time-input"
                                                    />
                                                    <span className="time-separator">to</span>
                                                    <input
                                                        type="time"
                                                        value={range.close}
                                                        onChange={(e) => updateTimeRange(day, index, 'close', e.target.value)}
                                                        className="time-input"
                                                    />
                                                </div>

                                                {schedule[day].timeRanges.length > 1 && (
                                                    <button
                                                        type="button"
                                                        onClick={() => removeTimeRange(day, index)}
                                                        className="btn-remove-range"
                                                        title="Remove this time range"
                                                    >
                                                        ✕
                                                    </button>
                                                )}
                                            </div>
                                        ))}
                                    </div>
                                ) : (
                                    <span className="closed-label">Closed</span>
                                )}
                            </div>
                        ))}
                    </div>

                    <button type="submit" className="btn btn-primary btn-block">
                        Create Restaurant
                    </button>
                </form>
            </div>
        </div>
    );
}