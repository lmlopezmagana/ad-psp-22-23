
# Ejemplo 9 - Ejemplo una asociación _One To Many_ (bidireccional)

## Asociaciones _uno-a-muchos_

Normalmente, cuando se estudia UML, se suelen aprender las asociaciones y su multiplicidad (en ocasiones, conocida como _cardinalidad_ ) sin tener en cuenta la dirección. Por ejemplo, en el siguiente
diagrama:

![Diagrama](../08_ManyToOne/uml.jpg)

La asociación `está-en` entre las clases `Alumno` y `Curso` se suele decir que es una asociación _uno a muchos_. Sin embargo, viendo el ejemplo 7, hemos comprobado que depende de la forma en que leamos la asociación, será de un tipo u otro:

- Si la leemos desde `Alumno` a `Curso`, tendremos una asociación _muchos-a-uno_.
- Si la leemos desde `Curso` a `Alumno`, tendremos una asociación _uno-a-muchos_.

**¿Qué alternativas de tratamiento tenemos en estos casos?** Ciertamente, JPA nos ofrece varias formas de implementar una asociación uno a muchos. Cada una con sus ventajas y desventajas:

| **Estrategia** 	| Ventajas 	| Desventajas 	|
|------------	|----------	|-------------	|
| Implementar solo la asociación _muchos-a-uno_ (solo utilizados `@ManyToOne`) | Implementación fácil. Basta incluir la anotación `@ManyToOne`en el lado muchos, y JPA crea todo lo necesario por nosotros. 	| En el lado uno, no tenemos conocimiento de las instancias del lado muchos con la cuál está asociada 	|
| Implementar solo la asociación _uno-a-muchos_ (solo usamos `OneToMany`) | Implementación fácil. Tan solo usamos la anotación `@OneToMany` en el lado uno. Esta anotación va asociada a una colección (de tipo `List` o `Set`| Muy bajo rendimiento. Se genera una tabla intermedia, y se realizan múltiples operaciones para inserción, actualización y consulta. 	|
| **Implementación _bidireccional_: se utiliza la anotación `@ManyToOne` en el lado muchos, y `@OneToMany` en el lado uno.**  | Toda la asociación se maneja a través de una _clave externa_, con lo que ganamos en rendimiento. | Implementación más compleja. Requiere de métodos auxiliares que mantengan el estado de la asociación en ambos extremos. 	|
| **Implementar solo la asociación _muchos-a-uno_ y utilizar consultas para obtener la información del lado _uno-a-muchos_** | Fácil implementación de entidades. Solo _penalizamos_ el rendimiento del sistema en el momento de consultar. Muy útil para aquellos que saben SQL/JPQL | Implementación de consultas más compleja.

Como podemos ver en el énfasis del texto, por cuestiones de rendimiento las mejores opciones son las dos últimas. En este ejemplo veremos la **implementación bidireccional**.

## Paso 1: Implementación de la asociación en el lado _muchos_

Ya la tenemos del ejemplo anterior:

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

    @ManyToOne
    @JoinColumn(name = "curso", foreignKey = @ForeignKey(name = "FK_ALUMNO_CURSO"))
    private Curso curso;

    // Resto del código


}

```

## Paso 2: Implementación de la asociación en el lado _uno_

### Algo de teoría

Como hemos visto antes, la anotación que ofrece JPA para las asociaciones _uno-a-muchos_ es `@OneToMany`. Dicha anotación debe ir asociada a una colección. Si revisamos la documentación o la literatura al respecto, encontramos:

- En el caso de las asociaciones `@OneToMany` unidireccionales, recomiendan el uso de `Set`.
- En el caso de las asociaciones `@OneToMany` bidireccionales, podemos utilizar `List` sin ver penalizado el rendimiento.

Por tanto, tendríamos que tener algo parecido a esto:

```java
@OneToMany
private List<Alumno> alumnos;
```

Sin embargo, con esta información no basta. **Como queremos darle a nuestra asociación un tratamiento bidireccional**, necesitamos indicar que esta asociación _uno-a-muchos_ viene _mapeada_ por una asociación _muchos-a-uno_. La forma de hacerlo es mediante el atributo `mappedBy` de la propia anotación. **Tenemos que indicar el nombre del atributo de la clase opuesta (en nuestro caso, `Alumno`, que está anotado con `@ManyToOne`)**.

`@ManyToOne`
```java
@ManyToOne
private Curso curso;
``` 

`@OneToMany`
```java
@OneToMany(mappedBy="curso")
private List<Alumno> alumnos;
```

### Métodos auxiliares para mantener ambos extremos de la asociación

El tratamiento bidireccional de la asociación no es gratuito. Si queremos dar a una asociación un tratamiento bidireccional, tendremos que **asignar la asociación en ambos extremos**. Para facilitar la operación, se suelen crear unos métodos auxiliares (en ocasiones conocidos como _helpers_) que realicen esta doble asignación.

**¿Dónde se implementan estos métodos?** Deben estar en una de las dos clases modelo. Normalmente, suelen estar en el lado **propietario** de la asociación.

> El lado **propietario** de una asociación tratada bidireccionalmente es el lado **no mapeado**, es decir, el lado que realmente va a persistir esta asociación a nivel de base de datos. En el ejemplo de many-to-one a one-to-many, el lado propietario es el anotado con `@ManyToOne`, que es el que genera la clave externa en la base de datos.

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

    @ManyToOne
    @JoinColumn(name = "curso", foreignKey = @ForeignKey(name = "FK_ALUMNO_CURSO"))
    private Curso curso;

    public void addCurso(Curso c) {
        this.curso = c;
        c.getAlumnos().add(this);
    }

    public void removeCurso(Curso c) {
        c.getAlumnos().remove(this);
        this.curso = null;
    }


}
```

## Paso 3: Cómo trabajar con ahora con `Alumno` y `Curso`

Para trabajar ahora con nuestras entidades asociadas, tendremos que usar los métodos que hemos definido en el paso anterior.

```java
Curso dam2 = Curso.builder()
                .nombre("2º DAM")
                .tutor("Luismi")
                .alumnos(new ArrayList<>())
                .build();

        cursoService.save(dam2);

        Alumno alumno = Alumno.builder()
                .nombre("Luismi")
                .apellidos("López")
                .email("luismi.lopez@salesianos.edu")
                //.curso(dam2)
                .build();

        dam2.getAlumnos().add(alumno);

        alumno.addCurso(dam2);

        service.save(alumno);

        System.out.printf("%s, %s\n", alumno.getNombre(), alumno.getCurso().getNombre());

        System.out.println("Nº de alumnos en el curso:" + dam2.getAlumnos().size());

        alumno.removeCurso(dam2);

        service.save(alumno);

        System.out.printf("%s, %s\n", alumno.getNombre(), alumno.getCurso() != null ? alumno.getCurso().getNombre() : "Sin curso");

        System.out.println("Nº de alumnos en el curso:" + dam2.getAlumnos().size());

        System.out.println(dam2);


```

Como podemos comprobar, para asociar un curso a una serie de alumnos, tenemos que usar el método helper correspondiente, y (en este caso) almacenar el lado _muchos_, que es quién realmente almacena a nivel de base de datos la asociación (la clave externa).

## Paso 4: ¡IMPORTANTE! Hay que evitar las referencias circulares

Como decíamos antes, el tratamiento bidireccional de una asociación **no es gratuito**. Otro problema que se nos presenta es el de los métodos `equals`, `hashCode` y `toString`. 

> Antes de nada, os recomiendo leer un artículo de Vlad Mihalcea, que es uno de los referentes que podemos encontrar por internet sobre JPA, Hibernate y Spring. Se trata de [https://vladmihalcea.com/the-best-way-to-implement-equals-hashcode-and-tostring-with-jpa-and-hibernate/](https://vladmihalcea.com/the-best-way-to-implement-equals-hashcode-and-tostring-with-jpa-and-hibernate/); este artículo nos ayuda a reflexionar sobre la mejor manera de implementar estos métodos cuando trabajamos con JPA e Hibernate. Si utilizamos la aproximación de Vlad Mihalcea, nos evitaríamos el problema que desarrollo en este apartado.

El problema que se nos presenta aquí es de como se implementan estos métodos. Supongamos el método `toString` de la clase `Curso`. _Grosso modo_, este método realiza una operación parecida a la siguiente:

```
resultado <- ""
Para toda propiedad p de la clase Curso
	resultado <- resultado + p.toString() 
devolver resultado
```

El problema lo tenemos cuando llega a tratar la propiedad `alumno`, ya que al llamar al método `toString` de este, y ser una lista, el tratamiento que se hace es invocar a `toString` de cada una de las instancias que contiene. A su vez, cada instancia de `Alumno` tiene una propiedad llamada `curso`. De hecho, ¡es el mismo curso sobre el que ya hemos invocado `toString`!. 

Como podemos ver, tenemos una **referencia circular**, que nos va a provocar **un desbordamiento de la pila.**

Una forma de solucionarlo es excluir de estos métodos (`equals`, `hashCode` y `toString`) la colección anotada con `@OneToMany`. _Lombok_ nos permite hacer esto fácilmente a través de las anotaciones `@EqualsAndHashcode.Exclude` y `@ToString.Exclude` sobre el atributo en cuestión.

```java
@EqualsAndHashCode.Exclude
@ToString.Exclude
@OneToMany(mappedBy="curso")
private List<Alumno> alumnos = new ArrayList<>();
```

**Otra forma de solucionar este problema** sería revisar el ejemplo 6 y estudiar cuándo nos interesa realmente implementar los métodos `equals`, `hashCode` y `toString`, ya que no siempre serán necesarios.


## Y, ¿cuál sería la otra estrategia de implementación de _uno-a-muchos_?

Como indicábamos en la tabla inicial, sería a través del uso de consultas. La estudiaremos cuando aprendamos a manejarlas.

## Algo de teoría sobre tipos de _fetching_

Ya veíamos en el un ejemplo anterior que JPA nos permitía indicar cuando sí queríamos _leer_ todos los campos de una entidad en la base de datos, y cuando queríamos hacerlo que solo se hiciera si lo pedimos explícitamente.

JPA ofrece dos tipos diferentes de fetching:

- EAGER: se trata del tipo _ansioso_. Si un atributo o asociación tiene este tipo, quiere decir que siempre se _traerá_ sus datos desde la base de datos.
- LAZY: se trata del tipo _perezoso_. Si un atributo o asociación tiene este tipo, solo se _traerá_ sus datos cuando lo solicitemos explícitamente.

Cuando usamos **asociaciones**, JPA aplica por defecto una estrategia de fetching, pero depende del tipo de asociación:

|Tipo de asociación | Tipo de _fecthing_ por defecto |
|-------------------|--------------------------------| 
| `@ManyToOne` | `EAGER` |
| `@OneToOne` | `EAGER` |
| `@OneToMany` | `LAZY` |
| `@ManyToMany` | `LAZY` |

## ¿Qué tipo de _fetching_ utilizar?

Por desgracia, la respuesta es **depende**. Si leemos el [artículo](https://vladmihalcea.com/hibernate-facts-the-importance-of-fetch-strategy/) de Vlad Mihalcea sobre este tema, encontramos la idea de que el diseño del _fetching_ tiene que venir asociado al diseño mismo de las clases y entidades, y no tiene que ser una tarea que dejemos para el final. Una mala estrategia en este sentido puede impactar en un mal rendimiento de nuestro sistema cuando este esté en producción.

En el caso de las asociaciones, nos deberíamos hacer la siguiente pregunta: si tengo 2 entidades, `A` y `B`, y ambas están asociadas: ¿siempre que quiera conseguir los datos de `A`, quiero obtener los de `B`? Si la respuesta es NO, quiere decir que no necesitamos el _fetching_ ansioso. Si al respuesta es SÍ, tendremos que pensar en el impacto que esto puede producir.

