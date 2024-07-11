-- Drop tables if they already exist
DROP TABLE IF EXISTS flower_images;
DROP TABLE IF EXISTS flowers;

-- Create the flowers table
CREATE TABLE flowers (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL
    created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(255) NOT NULL,
    modified_date TIMESTAMP,
    modified_by VARCHAR(255)
);

-- Create the flower_images table
CREATE TABLE flower_images (
    id SERIAL PRIMARY KEY,
    encrypted_image BYTEA NOT NULL,
    flower_id INTEGER NOT NULL,
    created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(255) NOT NULL,
    modified_date TIMESTAMP,
    modified_by VARCHAR(255),
    CONSTRAINT fk_flower
        FOREIGN KEY(flower_id)
        REFERENCES flowers(id)
        ON DELETE CASCADE
);

