import { DishType, DishTag, DishState } from '../types/types';

interface FilterBarProps {
    selectedType: DishType | 'ALL';
    onTypeChange: (type: DishType | 'ALL') => void;
    selectedTags: DishTag[];
    onTagsChange: (tags: DishTag[]) => void;
    selectedState: DishState | 'ALL';
    onStateChange: (state: DishState | 'ALL') => void;
    searchQuery: string;
    onSearchChange: (query: string) => void;
}

export default function FilterBar({
                                      selectedType,
                                      onTypeChange,
                                      selectedTags,
                                      onTagsChange,
                                      selectedState,
                                      onStateChange,
                                      searchQuery,
                                      onSearchChange,
                                  }: FilterBarProps) {
    const dishTypes: (DishType | 'ALL')[] = ['ALL', 'MAIN', 'STARTER', 'DESSERT'];
    const dishTags: DishTag[] = ['GLUTEN_FREE', 'VEGETARIAN', 'VEGAN'];
    const dishStates: (DishState | 'ALL')[] = ['ALL', 'PUBLISHED', 'DRAFT'];

    const toggleTag = (tag: DishTag) => {
        if (selectedTags.includes(tag)) {
            onTagsChange(selectedTags.filter(t => t !== tag));
        } else {
            onTagsChange([...selectedTags, tag]);
        }
    };

    return (
        <div className="filter-bar">
            <div className="filter-group">
                <label>Search:</label>
                <input
                    type="text"
                    placeholder="Search by name..."
                    value={searchQuery}
                    onChange={(e) => onSearchChange(e.target.value)}
                    className="search-input"
                />
            </div>

            <div className="filter-group">
                <label>Type:</label>
                <div className="filter-buttons">
                    {dishTypes.map(type => (
                        <button
                            key={type}
                            className={`filter-btn ${selectedType === type ? 'active' : ''}`}
                            onClick={() => onTypeChange(type)}
                        >
                            {type}
                        </button>
                    ))}
                </div>
            </div>

            <div className="filter-group">
                <label>Tags:</label>
                <div className="filter-checkboxes">
                    {dishTags.map(tag => (
                        <label key={tag} className="checkbox-label">
                            <input
                                type="checkbox"
                                checked={selectedTags.includes(tag)}
                                onChange={() => toggleTag(tag)}
                            />
                            {tag.replace('_', ' ')}
                        </label>
                    ))}
                </div>
            </div>

            <div className="filter-group">
                <label>Status:</label>
                <div className="filter-buttons">
                    {dishStates.map(state => (
                        <button
                            key={state}
                            className={`filter-btn ${selectedState === state ? 'active' : ''}`}
                            onClick={() => onStateChange(state)}
                        >
                            {state}
                        </button>
                    ))}
                </div>
            </div>
        </div>
    );
}