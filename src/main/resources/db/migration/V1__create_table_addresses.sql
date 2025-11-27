CREATE TABLE IF NOT EXISTS addresses (
    id SERIAL PRIMARY KEY,
    street VARCHAR(255) NOT NULL,
    city VARCHAR(255) NOT NULL,
    zip_code VARCHAR(50) NOT NULL,
    status VARCHAR(20) NOT NULL,

    latitude DOUBLE PRECISION,
    longitude DOUBLE PRECISION,

    created_at TIMESTAMP,
    updated_at TIMESTAMP
);