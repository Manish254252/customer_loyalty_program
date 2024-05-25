CREATE DATABASE customer_loyalty;
USE customer_loyalty;


CREATE TABLE customers (
    customer_id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    email VARCHAR(100) UNIQUE,
    phone VARCHAR(15),
    join_date DATE,
    loyalty_points INT DEFAULT 0
);

CREATE TABLE transactions (
    transaction_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT,
    transaction_date DATE,
    amount DECIMAL(10, 2),
    points_earned INT,
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
);


CREATE TABLE rewards (
    reward_id INT AUTO_INCREMENT PRIMARY KEY,
    reward_name VARCHAR(100),
    points_required INT,
    description TEXT
);


CREATE TABLE redemptions (
    redemption_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT,
    reward_id INT,
    redemption_date DATE,
    points_deducted INT,
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id),
    FOREIGN KEY (reward_id) REFERENCES rewards(reward_id)
);
