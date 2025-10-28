INSERT INTO rooms (room_number, room_type, price) VALUES
('101', 'Individual', 50.00),
('102', 'Doble', 80.00),
('201', 'Suite', 150.00),
('202', 'Deluxe', 200.00);

INSERT INTO clients (full_name, email, phone, room_id) VALUES
('Carlos Ramírez', 'carlos.ramirez@email.com', '600123456', 1),
('Lucía Fernández', 'lucia.fernandez@email.com', '600987654', 2),
('Pedro Gómez', 'pedro.gomez@email.com', '600456789', 3),
('Ana Torres', 'ana.torres@email.com', '600321654', NULL); -- aún sin habitación

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
