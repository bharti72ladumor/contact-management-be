-- Create database
CREATE DATABASE IF NOT EXISTS contact_db;
USE contact_db;

-- Drop tables if they exist (for fresh start)
DROP TABLE IF EXISTS contacts;
DROP TABLE IF EXISTS users;

-- Create users table
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create contacts table
CREATE TABLE contacts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL,
    phone_number VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Insert sample users (passwords are bcrypt hashed versions of 'password123')
INSERT INTO users (username, email, password, created_at) VALUES
('testuser', 'testuser@example.com', '$2a$10$slYQmyNdGzin7olVN3p5be4DlH.PKZbv5H8KnzzVgXXbVxzy2QDFG', NOW()),
('admin', 'admin@example.com', '$2a$10$slYQmyNdGzin7olVN3p5be4DlH.PKZbv5H8KnzzVgXXbVxzy2QDFG', NOW());

-- Insert sample contacts
INSERT INTO contacts (name, address, phone_number, created_at) VALUES
('John Doe', '123 Main St, New York, NY', '+1-212-555-0100', NOW()),
('Jane Smith', '456 Oak Ave, Los Angeles, CA', '+1-213-555-0101', NOW()),
('Bob Johnson', '789 Pine Rd, Chicago, IL', '+1-312-555-0102', NOW());
