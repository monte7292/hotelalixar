INSERT INTO rooms (room_number, room_type, price) VALUES
('101', 'Individual', 50.00),
('102', 'Doble', 80.00),
('201', 'Suite', 150.00),
('202', 'Deluxe', 200.00),
('203', 'Suite Deluxe', 220.00);

INSERT INTO clients (full_name, email, phone, room_id) VALUES
-- Habitación 1
('Carlos Ramírez', 'carlos.ramirez@email.com', '600123456', 1),
('María López', 'maria.lopez@email.com', '600654321', 1),
-- Habitación 2
('Lucía Fernández', 'lucia.fernandez@email.com', '600987654', 2),
('Javier Ruiz', 'javier.ruiz@email.com', '600777888', 2),
-- Habitación 3
('Pedro Gómez', 'pedro.gomez@email.com', '600456789', 3),
('Laura Sánchez', 'laura.sanchez@email.com', '600999000', 3),
-- Habitación 4
('Andrés Martínez', 'andres.martinez@email.com', '600555444', 4),
('Paula Vega', 'paula.vega@email.com', '600555333', 4),
-- Habitación 5
('Sergio Navarro', 'sergio.navarro@email.com', '600444555', 5),
('Elena Morales', 'elena.morales@email.com', '600333222', 5),
-- Sin habitación asignada
('Ana Torres', 'ana.torres@email.com', '600321654', NULL),
('Marta Díaz', 'marta.diaz@email.com', '600222111', NULL),
('Roberto León', 'roberto.leon@email.com', '600888777', NULL);


INSERT INTO employees (full_name) VALUES
('María López'),
('Javier Ruiz'),
('Sandra Morales'),
('Andrés Castillo');

INSERT INTO services (service_name, description, price, employee_id) VALUES
('Limpieza diaria', 'Servicio de limpieza y arreglo de habitación.', 20.00, 1),
('Masaje relajante', 'Masaje corporal completo de 60 minutos.', 50.00, 3),
('Desayuno buffet', 'Desayuno completo servido en el restaurante.', 15.00, 2),
('Servicio de lavandería', 'Lavado y planchado de ropa personal.', 25.00, 4),
('Transporte al aeropuerto', 'Traslado privado al aeropuerto local.', 35.00, 4);

INSERT INTO client_services (client_id, service_id, service_date) VALUES
(1, 1, '2025-10-25'),  -- Carlos usó limpieza diaria
(1, 3, '2025-10-26'),  -- Carlos tomó desayuno buffet
(2, 2, '2025-10-27'),  -- Lucía recibió masaje
(2, 3, '2025-10-27'),  -- Lucía también tomó desayuno
(3, 4, '2025-10-28'),  -- Pedro usó lavandería
(3, 5, '2025-10-28');  -- Pedro usó transporte
