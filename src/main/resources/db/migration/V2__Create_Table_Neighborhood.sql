CREATE TABLE neighborhoods (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    city_id BIGINT NOT NULL,
    zone VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_neighborhood_city
        FOREIGN KEY (city_id)
        REFERENCES cities(id)
        ON DELETE CASCADE
);
