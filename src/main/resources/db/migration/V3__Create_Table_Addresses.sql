CREATE TABLE addresses (
    id BIGSERIAL PRIMARY KEY,

    street VARCHAR(255) NOT NULL,
    number VARCHAR(10),
    complement VARCHAR(100),

    neighborhood_id BIGINT NOT NULL,

    zip_code VARCHAR(9),
    reference_point VARCHAR(255),

    status VARCHAR(20) NOT NULL,

    latitude DOUBLE PRECISION,
    longitude DOUBLE PRECISION,

    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_address_neighborhood
        FOREIGN KEY (neighborhood_id)
        REFERENCES neighborhoods(id)
        ON DELETE CASCADE
);
