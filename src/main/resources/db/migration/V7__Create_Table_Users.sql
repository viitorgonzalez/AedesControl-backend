CREATE TABLE users (
    user_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    user_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,

    city_id BIGINT NOT NULL,

    CONSTRAINT fk_user_city
        FOREIGN KEY (city_id)
        REFERENCES cities(id)
        ON DELETE RESTRICT
);
