CREATE TABLE franchise (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    created_date TIMESTAMP
);

CREATE TABLE branch (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    franchise_id BIGINT NOT NULL,
    name VARCHAR(100) NOT NULL,
    address VARCHAR(255),
    phone VARCHAR(20),
    created_date TIMESTAMP,

    CONSTRAINT fk_branch_franchise
        FOREIGN KEY (franchise_id)
        REFERENCES franchise(id)
        ON DELETE CASCADE
);

CREATE TABLE product (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    branch_id BIGINT NOT NULL,
    name VARCHAR(100) NOT NULL,
    price NUMERIC(10,2) NOT NULL,
    stock INT NOT NULL,
    created_date TIMESTAMP,

    CONSTRAINT fk_product_branch
        FOREIGN KEY (branch_id)
        REFERENCES branch(id)
        ON DELETE CASCADE
);

INSERT INTO franchise (name, description, created_date) VALUES
('Fast Food Nation', 'Fast food restaurant franchise', CURRENT_TIMESTAMP),
('Tech Coffee', 'Technology-themed coffee shop franchise', CURRENT_TIMESTAMP),
('Healthy Bites', 'Healthy food and juice franchise', CURRENT_TIMESTAMP);


INSERT INTO branch (franchise_id, name, address, phone, created_date) VALUES
(1, 'Fast Food Nation - Downtown', '123 Main Street, New York', '+1-212-555-0101', CURRENT_TIMESTAMP),
(1, 'Fast Food Nation - Uptown', '456 Broadway Ave, New York', '+1-212-555-0102', CURRENT_TIMESTAMP),
(2, 'Tech Coffee - Silicon Valley', '789 Innovation Dr, San Jose', '+1-408-555-0201', CURRENT_TIMESTAMP),
(2, 'Tech Coffee - Seattle', '321 Cloud St, Seattle', '+1-206-555-0202', CURRENT_TIMESTAMP),
(3, 'Healthy Bites - Miami Beach', '654 Ocean Drive, Miami', '+1-305-555-0301', CURRENT_TIMESTAMP);


INSERT INTO product (branch_id, name, price, stock, created_date) VALUES
(1, 'Classic Burger', 8.99, 150, CURRENT_TIMESTAMP),
(1, 'French Fries', 3.49, 300, CURRENT_TIMESTAMP),
(1, 'Cola Drink', 1.99, 500, CURRENT_TIMESTAMP),

(2, 'Cheese Burger', 9.49, 120, CURRENT_TIMESTAMP),
(2, 'Onion Rings', 3.99, 200, CURRENT_TIMESTAMP),

(3, 'Espresso', 2.99, 250, CURRENT_TIMESTAMP),
(3, 'Cappuccino', 3.99, 200, CURRENT_TIMESTAMP),
(3, 'Blueberry Muffin', 2.49, 100, CURRENT_TIMESTAMP),

(4, 'Latte', 4.49, 180, CURRENT_TIMESTAMP),
(4, 'Cold Brew', 4.99, 160, CURRENT_TIMESTAMP),

(5, 'Green Smoothie', 6.99, 140, CURRENT_TIMESTAMP),
(5, 'Avocado Salad', 7.99, 90, CURRENT_TIMESTAMP);