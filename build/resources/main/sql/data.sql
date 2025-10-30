-- Insert sample restaurants
INSERT INTO restaurants (uuid, owner, restaurant_name, street, number, postal_code, city, email_address, pictures, cuisine_type, default_preparation_time, opening_hours, manual_status)
VALUES
    ('550e8400-e29b-41d4-a716-446655440000', '123e4567-e89b-12d3-a456-426614174000', 'The Golden Spoon', 'Main Street', '123', '2000', 'Antwerp', 'contact@goldenspoon.be',
     '[{"url": "https://example.com/golden-spoon.jpg"}]', 'FRENCH', 30,
     '{"openingHours": {"MONDAY": [{"open": "11:00", "close": "22:00"}], "TUESDAY": [{"open": "11:00", "close": "22:00"}], "WEDNESDAY": [{"open": "11:00", "close": "23:55"}], "THURSDAY": [{"open": "11:00", "close": "22:00"}]}}',
     'OPEN'),
    ('660e8400-e29b-41d4-a716-446655440001', '123e4567-e89b-12d3-a456-426614174001', 'Sushi Paradise', 'Park Avenue', '45', '2018', 'Antwerp', 'info@sushiparadise.be',
     '[{"url": "https://example.com/sushi-paradise.jpg"}]', 'JAPANESE', 25,
     '{"openingHours": {"MONDAY": [{"open": "12:00", "close": "22:00"}], "TUESDAY": [{"open": "12:00", "close": "22:00"}], "WEDNESDAY": [{"open": "12:00", "close": "22:00"}], "THURSDAY": [{"open": "12:00", "close": "22:00"}]}}',
     'OPEN'),
    ('770e8400-e29b-41d4-a716-446655440002', '123e4567-e89b-12d3-a456-426614174002', 'Pasta Bella', 'Garden Street', '67', '2020', 'Antwerp', 'hello@pastabella.be',
     '[{"url": "https://example.com/pasta-bella.jpg"}]', 'ITALIAN', 20,
     '{"openingHours": {"MONDAY": [{"open": "11:30", "close": "21:30"}], "TUESDAY": [{"open": "11:30", "close": "21:30"}], "WEDNESDAY": [{"open": "11:30", "close": "21:30"}], "THURSDAY": [{"open": "11:30", "close": "21:30"}]}}',
     'OPEN');


-- Insert sample dishes
INSERT INTO dishes (dish_id, parent_id, name, dish_type, tags, description, price, picture_url, state, publish_at, restaurant_uuid)
VALUES
    -- Dishes for The Golden Spoon
    ('880e8400-e29b-41d4-a716-446655440010', NULL, 'Grilled Salmon', 'MAIN', '{GLUTEN_FREE}', 'Fresh Atlantic salmon with seasonal vegetables', 24.99, 'https://example.com/salmon.jpg', 'PUBLISHED', NULL, '550e8400-e29b-41d4-a716-446655440000'),
    ('880e8400-e29b-41d4-a716-446655440011', NULL, 'Caesar Salad', 'STARTER', '{VEGETARIAN}', 'Classic Caesar salad with parmesan and croutons', 12.50, 'https://example.com/caesar.jpg', 'PUBLISHED', NULL, '550e8400-e29b-41d4-a716-446655440000'),
    ('880e8400-e29b-41d4-a716-446655440012', NULL, 'Chocolate Lava Cake', 'DESSERT', '{VEGETARIAN}', 'Warm chocolate cake with vanilla ice cream', 8.99, 'https://example.com/lava-cake.jpg', 'PUBLISHED', NULL, '550e8400-e29b-41d4-a716-446655440000'),

    -- Dishes for Sushi Paradise
    ('880e8400-e29b-41d4-a716-446655440020', NULL, 'Dragon Roll', 'MAIN', '{GLUTEN_FREE}', 'Eel, avocado, cucumber topped with avocado and eel sauce', 16.99, 'https://example.com/dragon-roll.jpg', 'PUBLISHED', NULL, '660e8400-e29b-41d4-a716-446655440001'),
    ('880e8400-e29b-41d4-a716-446655440021', NULL, 'Miso Soup', 'STARTER', '{VEGAN,GLUTEN_FREE}', 'Traditional Japanese miso soup with tofu and seaweed', 4.50, 'https://example.com/miso.jpg', 'PUBLISHED', NULL, '660e8400-e29b-41d4-a716-446655440001'),
    ('880e8400-e29b-41d4-a716-446655440022', NULL, 'Green Tea Ice Cream', 'DESSERT', '{VEGETARIAN,GLUTEN_FREE}', 'Authentic matcha green tea ice cream', 5.99, 'https://example.com/green-tea-ice.jpg', 'PUBLISHED', NULL, '660e8400-e29b-41d4-a716-446655440001'),

    -- Dishes for Pasta Bella
    ('880e8400-e29b-41d4-a716-446655440030', NULL, 'Spaghetti Carbonara', 'MAIN', '{}', 'Traditional Italian carbonara with pancetta and pecorino', 14.99, 'https://example.com/carbonara.jpg', 'PUBLISHED', NULL, '770e8400-e29b-41d4-a716-446655440002'),
    ('880e8400-e29b-41d4-a716-446655440031', NULL, 'Bruschetta', 'STARTER', '{VEGAN}', 'Toasted bread with fresh tomatoes, basil and olive oil', 7.50, 'https://example.com/bruschetta.jpg', 'PUBLISHED', NULL, '770e8400-e29b-41d4-a716-446655440002'),
    ('880e8400-e29b-41d4-a716-446655440032', NULL, 'Tiramisu', 'DESSERT', '{VEGETARIAN}', 'Classic Italian tiramisu with mascarpone and coffee', 6.99, 'https://example.com/tiramisu.jpg', 'PUBLISHED', NULL, '770e8400-e29b-41d4-a716-446655440002'),
    ('880e8401-e29b-41d4-a716-446655440033', '880e8400-e29b-41d4-a716-446655440032', 'Margherita Pizza', 'MAIN', '{VEGETARIAN}', 'Classic pizza with tomato sauce, mozzarella and basil', 11.99, 'https://example.com/margherita.jpg', 'DRAFT', '2025-02-01 10:00:00', '770e8400-e29b-41d4-a716-446655440002');
