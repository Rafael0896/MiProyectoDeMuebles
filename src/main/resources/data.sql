-- =================================================================
-- 1. DATOS DEL CATÁLOGO DE PRODUCTOS
-- =================================================================

-- Insertar Categorías
INSERT INTO categories (name, description, active) VALUES
                                                       ('Sofás', 'Sofás cómodos y de diseño para tu sala de estar.', true),
                                                       ('Mesas', 'Mesas de comedor, de centro y auxiliares.', true),
                                                       ('Sillas', 'Sillas para comedor, oficina y más.', true),
                                                       ('Almacenamiento', 'Estanterías, armarios y soluciones de almacenamiento.', true);

-- Insertar Productos
INSERT INTO products (name, description, price, stock, image_url, active, category_id, featured) VALUES
                                                                                                     ('Sofá Modular Gris', 'Un sofá espacioso y versátil con tapicería de tela gris resistente. Perfecto para familias.', 4039000, 15, 'https://media.falabella.com/sodimacCO/691460_01/w=800,h=800,fit=pad', true, 1, false),
                                                                                                     ('Mesa de Comedor de Roble', 'Mesa de madera maciza de roble con capacidad para 6 personas. Estilo nórdico.', 4999900, 10, 'https://media.falabella.com/falabellaCO/130271651_01/w=1500,h=1500,fit=pad', true, 2, false),
                                                                                                     ('Silla de Oficina Ergonómica', 'Silla con soporte lumbar ajustable, reposabrazos y base giratoria. Ideal para largas jornadas de trabajo.', 750000, 30, 'https://media.falabella.com/falabellaCO/124295621_01/w=1500,h=1500,fit=pad', true, 3, false),
                                                                                                     ('Estantería de Pared Industrial', 'Estantería con estructura de metal negro y baldas de madera. Diseño moderno y funcional.', 150000, 25, 'https://media.falabella.com/falabellaCO/133608261_01/w=1500,h=1500,fit=pad', true, 4, false),
                                                                                                     ('Sofá Cama Azul Marino', 'Sofá de 3 plazas que se convierte fácilmente en una cómoda cama. Tapicería de terciopelo azul.', 1700560, 8, 'https://media.falabella.com/falabellaCO/130508557_01/w=1500,h=1500,fit=pad', true, 1, false),
                                                                                                     ('Juego de 4 Sillas de Comedor', 'Set de cuatro sillas de diseño Eames en color blanco. Patas de madera de haya.', 489000, 20, 'https://media.falabella.com/falabellaCO/120274787_01/w=1500,h=1500,fit=pad', true, 3, false);

-- =================================================================
-- 2. DATOS DE LOS BANNERS PROMOCIONALES
-- =================================================================

-- Banner 1: Un banner principal que siempre está activo (sin fechas de inicio/fin).
INSERT INTO banners (title, subtitle, image_url, link_url, link_text, active, type, sort_order, created_at) VALUES
    ('Bienvenido a CreaMuebles', 'Diseño y calidad que transforman tu hogar', 'https://d1csarkz8obe9u.cloudfront.net/posterpreviews/furniture-banner-template-design-a636dbc0cd8fcad1e4f5c65dc3746501_screen.jpg?ts=1609919679', '/products', 'Ver Productos', true, 'MAIN', 1, NOW());

-- Banner 2: Una promoción que estuvo activa en el pasado (ya no debería aparecer).
INSERT INTO banners (title, subtitle, image_url, link_url, link_text, active, type, start_date, end_date, sort_order, created_at) VALUES
    ('¡Oferta de Invierno!', 'Hasta 30% de descuento en sofás seleccionados', 'https://d1csarkz8obe9u.cloudfront.net/posterpreviews/furniture-banner-template-design-a636dbc0cd8fcad1e4f5c65dc3746501_screen.jpg?ts=1609919679', '/category/1', 'Ir a Sofás', true, 'PROMOTION', '2023-07-01 00:00:00', '2023-07-31 23:59:59', 2, NOW());

-- Banner 3: Una promoción que está activa AHORA MISMO.
INSERT INTO banners (title, subtitle, image_url, link_url, link_text, active, type, start_date, end_date, sort_order, created_at) VALUES
    ('Nueva Colección de Mesas', 'Descubre los diseños que marcan tendencia', 'https://propiedades.com.co/wp-content/uploads/2022/05/Propiedades-Comedore-en-tendencia-Banner-Principal.webp', '/category/2', 'Ver Mesas', true, 'PROMOTION', '2024-05-01 00:00:00', '2024-06-30 23:59:59', 3, NOW());

-- Banner 4: Un banner que se activará en el futuro.
INSERT INTO banners (title, subtitle, image_url, link_url, link_text, active, type, start_date, end_date, sort_order, created_at) VALUES
    ('Próximamente: Black Friday', 'Prepárate para las mejores ofertas del año', 'https://img.freepik.com/vector-premium/diseno-banner-plantilla-tienda-electrodomesticos_958026-97.jpg', '/', 'Más Información', true, 'PROMOTION', '2024-11-20 00:00:00', '2024-11-29 23:59:59', 4, NOW());