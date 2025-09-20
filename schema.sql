CREATE DATABASE LeaveSystem;

USE LeaveSystem;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50),
    password VARCHAR(50),
    role VARCHAR(10) -- 'Admin' or 'Employee'
);

CREATE TABLE leaves (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    from_date DATE,
    to_date DATE,
    reason TEXT,
    status VARCHAR(10) DEFAULT 'Pending', -- 'Pending', 'Approved', 'Rejected'
    FOREIGN KEY (user_id) REFERENCES users(id)
);
