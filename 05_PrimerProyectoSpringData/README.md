
# Ejemplo 5 - Primer ejemplo con Spring Data JPA

Este es el primer ejemplo en el que integramos el acceso a base de datos con 
Spring Data JPA. Para crearlo, hemos utilizado el asistente de Spring 
Starter (en la web). Hemos añadido las siguientes dependencias (las hemos 
añadido a través de dicho asistente, y este es el resultado en el `pom.xml`:

```xml
	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>
	    <dependency>
			<groupId>com.h2database</groupId>
			<artifactId>h2</artifactId>
			<scope>runtime</scope>
		</dependency>
		<dependency>
			<groupId>org.projectlombok</groupId>
			<artifactId>lombok</artifactId>
			<optional>true</optional>
		</dependency>
    
        <!-- resto de dependencias -->
	</dependencies>
```

A partir de esto, podemos observar que estamos creando un proyecto *web* que va a realizar acceso a datos a través de *data jpa*, que hará uso de *lombok*, y con una base de datos especial llamada *H2*, que también queda incluida dentro del proyecto.

## Configuración a través de _application.properties_

Aunque Spring Boot, al detectar las dependencias de Spring Data JPA y de H2, 
nos ofrece una configuración de base de datos por defecto, podemos 
configurar una serie de propiedades para afinar la misma.

- `spring.datasource.url`: es la URL jdbc de conexión a la base de datos. En la [documentación de H2](https://www.h2database.com/html/main.html) podemos encontrar más información.
- `spring.datasource.username` y `spring.datasource.password` son el usuario/contraseña con el que accedemos a la base de datos. Para H2, por defecto es _sa_ (Sys Admin) y sin contraseña.
- `spring.jpa.hibernate.ddl-auto`: indicamos la estrategia de generación del DDL (el esquema) de la base de datos. 
- `spring.jpa.show-sql`: indica si queremos que se muestren por consola las consultas SQL que se van ejecutando _por detrás_.
- `spring.h2.console.enabled`: indicamos si queremos habilitar la consola de H2, un cliente web para acceder a la base de datos.

## Estructura de paquetes

El ejemplo está estructurado en 3 simples paquetes.

* `com.salesianostriana.dam.springdata`: es el paquete raiz, que incluye la 
  clase principal.
* `com.salesianostriana.dam.springdata.model`: paquete en el cual vamos a 
  definir 
  todas las entidades de nuestro proyecto.
* `com.salesianostriana.dam.springdata.repos`: paquete en el que 
  definimos los repositorios de este proyecto.

## Entidades

Las entidades son objetos de nuestra aplicación que serán **mapeados** a la base de datos. Es decir, como normal general (aunque hay excepciones), por cada entidad encontraremos una tabla en nuestra base de datos relacional.

En el caso de este ejemplo:

```java
@Entity
public class Alumno {
	
	@Id
	@GeneratedValue
	private long id;
	
	private String nombre;
	private String apellidos;
	private String email;

}
```

Esta entidad será mapeada con una tabla en una base de datos relacional con la siguiente estructura:

- ALUMNO(id, nombre, apellidos, email).

### Identificador (Clave primaria)

La clave primaria de una entidad la denotaremos a través de la anotación `@Id` sobre el atributo.

#### Generación automática de la clave primaria

En muchos contextos, nos resultará muy cómodo gestionar las claves primarias de nuestras entidades a través de un campo _autonumérico_, es decir, a través de un número entero que comience en 1 y que se vaya incrementando automáticamente. Para ello completamos la anotación `@Id` con `@GeneratedValue`. Por ahora, escogemos la estrategia de generación automática; Spring Data JPA escoge, para el motor de base de datos que estemos usando, la mejor estrategia de generación (en el caso de H2, es una secuencia _general_ que utiliza para todas las tablas).

## Repositorios

Se tratan de nuestros objetos de acceso a datos (DAO, por sus siglas en inglés), con métodos como `save`, `delete`, `update`, ...

En Spring Data JPA, para crear un DAO tan solo tenemos que crear una interfaz que extienda a `CrudRepository` o a algunas de sus subinterfaces, como  **`JpaRepository`**.

```java
public interface AlumnoRepository extends JpaRepository<Alumno, Long> {

}
```

Tenemos que indicar dos tipos de datos:

* Primero, el tipo de dato _entidad_ que va a gestionar el DAO
* Segundo, el tipo de dato del Id de dicha entidad.

Nótese que si bien Spring define el estereotipo de bean `@Repository`, en este caso no es necesario usar dicha anotación.

## Anotaciones a nivel de clase

JPA nos ofrece la anotación [`@Table`](https://docs.oracle.com/javaee/7/api/javax/persistence/Table.html), con las siguientes propiedades:

- `name`: nombre de la tabla. Si no se especifica, se utiliza una estrategia de nombrado por defecto (que suele variar en función del  [dialecto](http://docs.jboss.org/hibernate/stable/annotations/api/org/hibernate/dialect/package-summary.html)).
- `schema`, `catalog`: nombre del esquema o catálogo de la base de datos donde estará la tabla mapeada.
- `indexes`, `uniqueConstraints`: listado de índices o de restricciones de unicidad.


```java
@Entity
@Table(name="STUDENT")
public class Alumno {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	private String nombre;
	private String apellidos;
	private String email;

}
```

## Anotaciones a nivel de atributo

Como hemos comprobado, salvo el atributo anotado con `@Id`, hemos comprobado que no es obligatorio el uso de ningún otra anotación. Con todo, JPA nos ofrece otras dos para poder gestionar algunos aspectos:

- `@Basic`: con dos posibles propiedades
  - `optional`: indica si el atributo debe ser obligatorio o no. Normalmente, implica una restricción `NOT NULL` a nivel del DDL.
  - `fetchType`: si bien ya hablaremos más adelante, indica el _tipo de lectura_ del atributo: si el atributo se obtendrá siempre (por defecto) o solo cuando se pida explícitamente. Por ahora, puedes encontrar más información [aquí](https://www.arquitecturajava.com/jpa-basic-optimizando-los-fechings/).
- `@Column`: con varias propiedades, que suelen afectar al DDL:
  - `columnDefinition`: un `String` con el trozo de código de DDL que se usará para generar la columna en la tabla.
  - `insertable`: Si la columna se incluye en las instrucciones SQL INSERT generadas por el proveedor de persistencia.
  - `name`: nombre de la columna
  - `length`: longitud de la columna
  - `nullable`: si la columna permite valores nulos
  - `preciosion`, `scale`: precisión y escala para valores numéricos
  - `table`: nombre de la tabla que contiene la columna
  - `unique`: si la columna es una clave única
  - `updatable`: Si la columna se incluye en las sentencias de SQL UPDATE generadas por el proveedor de persistencia.


Un ejemplo de uso de las anotaciones anteriores podría ser:

```java
@Entity
@Table(name="STUDENT")
public class Alumno {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@Basic(optional=false)
	@Column(name="FIRST_NAME")
	private String nombre;
	@Column(name="LAST_NAME", nullable=false)
	private String apellidos;
	@Column(unique=true)
	private String email;

}
``` 

El DDL generado como resultado sería:

```sql
create sequence hibernate_sequence
start with 1 
increment by 1

create table student (
	id bigint not null, 
	last_name varchar(255) not null, 
	email varchar(255), 
	first_name varchar(255) not null, 
	primary key (id)
)

alter table student 
add constraint UK_fe0i52si7ybu0wjedj6motiim unique (email)
```
