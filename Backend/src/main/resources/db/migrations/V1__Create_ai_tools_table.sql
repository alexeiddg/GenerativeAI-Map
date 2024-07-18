CREATE TABLE ai_tools (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    url VARCHAR(255) NOT NULL,
    rating VARCHAR(10),
    category VARCHAR(255),
    UNIQUE (name, url)
);