# Ejemplo 8 - Asociación `@ManyToOne`

## ¿Qué tenemos ya hecho?

Ya tenemos creada una entidad, así como su repositorio, un servicio y un controlador con algunos métodos.

## ¿Qué vamos a crear?

1. Una nueva entidad, que está asociada con la anterior.
2. La asociación entre ambas entidades.

## ¿Cuál es nuestro modelo de datos?

![diagrama uml](./uml.jpg) 

## Algo de teoría sobre asociaciones _ManyToOne_

Una asociación _muchos-a-uno_ es una asociación que relaciona dos clases, de forma que una instancia del lado _uno_ (en nuestro caso, curso), puede asociarse con varias instancias del lado _muchos_, (en nuestro caso, los alumnos).

Para poder implementar esto en nuestra aplicación necesitamos:

1. Trasladar la entidad del lado _muchos_.
2. Trasladar la entidad del lado _uno_.
3. Añadir los elementos necesarios para implementar la asociación.

### ¿Cómo impacta esto en el resto de nuestro sistema?

Como norma general:

- La entidad del lado _muchos_ tendrá su repositorio y su servicio.
- La entidad el lado _uno_ también tendrá su repositorio y su servicio.


## Paso 1: Creamos la segunda entidad

Es muy parecida a la que ya tenemos creada

```java
@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Curso {

    @Id
    @GeneratedValue
    private Long id;

    private String nombre;
    private String tutor;

    public Curso(String nombre, String tutor) {
        this.nombre = nombre;
        this.tutor = tutor;
    }
}
```


## Paso 2: Creamos el repositorio y servicio para esta entidad

```java
public interface CursoRepository 
		extends JpaRepository<Curso, Long> {

}
```

```java
@Service
public class CursoService extends BaseService<Curso, Long, CursoRepository> {
}

```

## Paso 3: Modificación de la clase `Alumno` para registrar la asociación entre clases

Para añadir los elementos necesarios para poder registrar la asociación entre ambas clases tenemos que modificar nuestra clase `Alumno`. Necesitamos saber, para cada instancia de `Alumno`, cual es su instancia de `Curso` correspondiente. Por tanto, tenemos que añadir una propiedad a la primera clase, de este segundo tipo.

```java
@Entity
@NoArgsConstructor
@Getter @Setter
@AllArgsConstructor @Builder
public class Alumno implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String nombre, apellidos, email;

    private Curso curso;


}

```

**Sin embargo, esto por sí solo no es suficiente**. JPA (en nuestro caso, Spring Data JPA) nos pide que identifiquemos esta asociación, para que la traslade al DDL (si corresponde) y la pueda manejar. Todo ello lo conseguimos a través de la anotación `@ManyToOne`.

El código, finalmente, quedaría así:

```java
Entity
@NoArgsConstructor
@Getter @Setter
@AllArgsConstructor @Builder
public class Alumno implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String nombre, apellidos, email;

    @ManyToOne
    @JoinColumn(name = "curso_id", foreignKey = @ForeignKey(name = "FK_ALUMNO_CURSO"))
    private Curso curso;


}

```

Si ejecutamos el proyecto, podemos apreciar el DDL generado

```sql
create table alumno (
	id bigint not null, 
	apellidos varchar(255), 
	email varchar(255), 
	nombre varchar(255), 
	curso_id bigint, 
	primary key (id)
);

create table curso (
	id bigint not null, 
	nombre varchar(255), 
	tutor varchar(255), primary key (id)
);

alter table alumno 
add constraint FK_ALUMNO_CURSO 
foreign key (curso_id) references curso;
```

Podemos apreciar como:

- Se añade un nuevo atributo, llamado `curso_id`, cuyo tipo es el mismo que el del Id de `Curso`.
- Se añade también una restricción de clave externa para este atributo.

