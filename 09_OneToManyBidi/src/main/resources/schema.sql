drop table if exists alumno CASCADE;
drop table if exists curso CASCADE;
drop sequence if exists hibernate_sequence;
create sequence hibernate_sequence start with 1 increment by 1;
create table alumno (id bigint not null, apellidos varchar(255), email varchar(255), nombre varchar(255), curso_id bigint, primary key (id));
create table curso (id bigint not null, nombre varchar(255), tutor varchar(255), primary key (id));
alter table alumno add constraint FKojks48ahsqwkx9o2s7pl0212p foreign key (curso_id) references curso;
