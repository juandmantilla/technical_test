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
    name VARCHAR(100) NOT NULL,
    price NUMERIC(10,2) NOT NULL,
    created_date TIMESTAMP
);


CREATE TABLE branch_product (
    branch_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    stock INT NOT NULL,

    PRIMARY KEY (branch_id, product_id),

    CONSTRAINT fk_bp_branch
        FOREIGN KEY (branch_id)
        REFERENCES branch(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_bp_product
        FOREIGN KEY (product_id)
        REFERENCES product(id)
        ON DELETE CASCADE
);

INSERT INTO franchise (name, description, created_date) VALUES
('Franquicia Norte', 'Franquicia zona norte', NOW());


INSERT INTO branch (franchise_id, name, address, phone, created_date) VALUES
(1, 'Sucursal Medellín', 'Calle 10 #20-30', '3001112233', NOW()),
(1, 'Sucursal Bogotá',  'Carrera 7 #45-60', '3002223344', NOW()),
(1, 'Sucursal Cali',    'Avenida 5 #12-90', '3003334455', NOW());


INSERT INTO product (name, price, created_date) VALUES
('Laptop',      3500.00, NOW()),
('Mouse',        80.00, NOW()),
('Teclado',     150.00, NOW()),
('Monitor',    1200.00, NOW()),
('Impresora',   900.00, NOW());
