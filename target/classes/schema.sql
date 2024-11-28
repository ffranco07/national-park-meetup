-- Create users table
CREATE TABLE users (
    user_id BIGINT PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'INACTIVE' CHECK (
        status IN (
            'ACTIVE', 
            'INACTIVE', 
            'DISABLED'
        )
    ),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create location table
CREATE TABLE location (
    location_id VARCHAR(255) PRIMARY KEY,
    park_name VARCHAR(255) NOT NULL,
	  state VARCHAR(20) NOT NULL,
    latitude DOUBLE PRECISION NOT NULL,
    longitude DOUBLE PRECISION NOT NULL
);

-- Create user_location table
 CREATE TABLE user_location (
    user_location_id BIGINT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    location_id BIGINT NOT NULL,
    updated_at TIMESTAMP,
    
    -- Define foreign key constraints
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    CONSTRAINT fk_location FOREIGN KEY (location_id) REFERENCES location(location_id) ON DELETE CASCADE
);

-- Inserts into USERS table
-- INSERT INTO USERS (user_id, password, email, status, created_at, updated_at)
-- VALUES
--  (1, '$2a$06$OAPObzhRdRXBCbk7Hj/ot.jY3zPwR8n7/mfLtKIgTzdJa4.6TwsIm', 'user1@mail.com', 'INACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--  (2, '$2a$06$OAPObzhRdRXBCbk7Hj/ot.jY3zPwR8n7/mfLtKIgTzdJa4.6TwsIm', 'user2@mail.com', 'INACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
--  (3, '$2a$06$OAPObzhRdRXBCbk7Hj/ot.jY3zPwR8n7/mfLtKIgTzdJa4.6TwsIm', 'user3@mail.com', 'INACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);





