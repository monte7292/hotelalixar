DROP TABLE IF EXISTS rooms;
DROP TABLE IF EXISTS clients;
DROP TABLE IF EXISTS employees;
DROP TABLE IF EXISTS services;
DROP TABLE IF EXISTS client_services;

CREATE TABLE IF NOT EXISTS rooms (
    room_id INT AUTO_INCREMENT PRIMARY KEY,
    room_number VARCHAR(10) NOT NULL,
    room_type VARCHAR(50),
    price DECIMAL(10,2) CHECK (price >= 0)
);

CREATE TABLE IF NOT EXISTS clients (
    client_id INT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE,
    phone VARCHAR(20),
    room_id INT,  -- Relación uno a uno con habitación
    FOREIGN KEY (room_id) REFERENCES rooms(room_id)
        ON DELETE SET NULL
        ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS employees (
    employee_id INT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS services (
    service_id INT AUTO_INCREMENT PRIMARY KEY,
    service_name VARCHAR(100) NOT NULL,
    description VARCHAR(300),
    price DECIMAL(10,2) CHECK (price >= 0),
    employee_id INT,  -- Relación uno a muchos con empleados
    FOREIGN KEY (employee_id) REFERENCES employees(employee_id)
        ON DELETE SET NULL
        ON UPDATE CASCADE
);

-- Tabla intermedia para la relación muchos a muchos entre clientes y servicios
CREATE TABLE IF NOT EXISTS client_services (
    client_id INT,
    service_id INT,
    service_date DATE DEFAULT CURRENT_DATE,
    PRIMARY KEY (client_id, service_id),
    FOREIGN KEY (client_id) REFERENCES clients(client_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    FOREIGN KEY (service_id) REFERENCES services(service_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);
