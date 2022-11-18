
insert into curso (id, nombre, tutor)
values (NEXTVAL('HIBERNATE_SEQUENCE'), '1º DAM', 'Miguel Campos');
insert into curso (id, nombre, tutor)
values (NEXTVAL('HIBERNATE_SEQUENCE'), '2º DAM', 'Luis Miguel López');

insert into alumno (id, nombre, apellidos, email, curso_id)
values (NEXTVAL('HIBERNATE_SEQUENCE'), 'Pepe', 'Gotera', 'pepe.gotera@salesianos.edu', 1);

insert into alumno (id, nombre, apellidos, email, curso_id)
values (NEXTVAL('HIBERNATE_SEQUENCE'), 'Otilio', 'el colega de Pepe', 'otilio.colega@salesianos.edu', 1);

insert into alumno (id, nombre, apellidos, email, curso_id)
values (NEXTVAL('HIBERNATE_SEQUENCE'), 'Mortadelo', 'sin apellidos', 'mortadelo@salesianos.edu', 2);

insert into alumno (id, nombre, apellidos, email, curso_id)
values (NEXTVAL('HIBERNATE_SEQUENCE'), 'Filemón', 'sin apellidos', 'filemon@salesianos.edu', 2);

insert into alumno (id, nombre, apellidos, email, curso_id)
values (NEXTVAL('HIBERNATE_SEQUENCE'), 'Zipi', 'de Pantuflo', 'zipi@salesianos.edu', 2);

insert into alumno (id, nombre, apellidos, email, curso_id)
values (NEXTVAL('HIBERNATE_SEQUENCE'), 'Zape', 'de Pantuflo', 'zape@salesianos.edu', 2);
