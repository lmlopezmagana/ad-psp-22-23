insert into categoria (id, nombre) values (1, 'Macbook');
insert into categoria (id, nombre) values (2, 'Dell XPS');
insert into producto (id, nombre, precio, categoria_id) values (1, 'Macbook Pro 14" M1 16gb/512gb', 1999,1);
insert into producto (id, nombre, precio, categoria_id) values (2, 'Macbook Pro 16" M1 16gb/512gb', 2499,1);
insert into imagen_producto (id, url, producto_id) values (1, 'http://laimagen.com', 1);
insert into imagen_producto (id, url, producto_id) values (2, 'http://laimagen.com/2', 1);
insert into imagen_producto (id, url, producto_id) values (3, 'http://laimagen.com/3', 1);
insert into imagen_producto (id, url, producto_id) values (4, 'http://laimagen.com/4', 2);

