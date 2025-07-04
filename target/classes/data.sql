-- 1. INSERTAR CATEGORÍAS PRIMERO
-- No es necesario añadir 'created_at', la base de datos lo hará sola gracias a la nueva configuración de la entidad.
INSERT INTO categories (name, description, active) VALUES
                                                       ('Sofás', 'Sofás cómodos y de diseño para tu sala de estar.', true),
                                                       ('Mesas', 'Mesas de comedor, de centro y auxiliares.', true),
                                                       ('Sillas', 'Sillas para comedor, oficina y más.', true),
                                                       ('Almacenamiento', 'Estanterías, armarios y soluciones de almacenamiento.', true);


-- En tu archivo data.sql
-- Se añade la columna 'featured' al final de la lista y se le da el valor 'false' a cada producto.
INSERT INTO products (name, description, price, stock, image_url, active, category_id, featured) VALUES
                                                                                                     ('Sofá Modular Gris', 'Un sofá espacioso y versátil con tapicería de tela gris resistente. Perfecto para familias.', 4039000, 15, 'https://media.falabella.com/sodimacCO/691460_01/w=800,h=800,fit=pad', true, 1, false),
                                                                                                     ('Mesa de Comedor de Roble', 'Mesa de madera maciza de roble con capacidad para 6 personas. Estilo nórdico.', 4999900, 10, 'https://media.falabella.com/falabellaCO/130271651_01/w=1500,h=1500,fit=pad', true, 2, false),
                                                                                                     ('Silla de Oficina Ergonómica', 'Silla con soporte lumbar ajustable, reposabrazos y base giratoria. Ideal para largas jornadas de trabajo.', 750000, 30, 'https://media.falabella.com/falabellaCO/124295621_01/w=1500,h=1500,fit=pad', true, 3, false),
                                                                                                     ('Estantería de Pared Industrial', 'Estantería con estructura de metal negro y baldas de madera. Diseño moderno y funcional.', 150000, 25, 'https://media.falabella.com/falabellaCO/133608261_01/w=1500,h=1500,fit=pad', true, 4, false),
                                                                                                     ('Sofá Cama Azul Marino', 'Sofá de 3 plazas que se convierte fácilmente en una cómoda cama. Tapicería de terciopelo azul.', 1700560, 8, 'https://media.falabella.com/falabellaCO/130508557_01/w=1500,h=1500,fit=pad', true, 1, false),
                                                                                                     ('Juego de 4 Sillas de Comedor', 'Set de cuatro sillas de diseño Eames en color blanco. Patas de madera de haya.', 489000, 20, 'https://media.falabella.com/falabellaCO/120274787_01/w=1500,h=1500,fit=pad', true, 3, false);