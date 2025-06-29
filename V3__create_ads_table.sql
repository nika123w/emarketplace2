CREATE TABLE ads (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    description TEXT NOT NULL,
    submission_time TIMESTAMP NOT NULL,
    photo_url TEXT,
    user_id UUID REFERENCES users(id)
);
