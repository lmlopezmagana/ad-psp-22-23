
# Ejemplo 12 - Grafos de entidad (_Entity Graphs_)

## El problema

JPA provee dos tipos de estrategia de _fetching_, es decir,de carga de datos de asociaciones (como `OneToMany`, `ManyToMany`, ...)

- `FetchType.LAZY`
- `FetchType.EAGER`

Esta configuración tiene la dificultad de que se declara de forma estática, y por tanto, aplica a todas las consultas que realicemos sobre esas entidades.

Supongamos el siguiente diagrama de clases:

![Diagrama de clases](./uml.jpg)

Esto nos lleva a tener las siguientes entidades:

```java
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Ciudad {

    @Id
    @GeneratedValue
    private Long id;

    private String nombre;
}


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Usuario {

    @Id
    @GeneratedValue
    private Long id;

    private String nombre, email, password, telefono;


}

@Entity
@NoArgsConstructor @AllArgsConstructor @Builder
@Getter @Setter
public class Direccion {

    @Id
    @GeneratedValue
    private Long id;

    private String tipo, calle, piso, codigoPostal;

    @OneToOne
    @JoinColumn(name = "ciudad_id")
    private Ciudad ciudad;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;


}

```

Supongamos que insertamos los siguientes registros en la base de datos

![Registros](registros.png)

Debemos tener presente que **`Direccion`** es el lado propietario en ambas asociaciones.

Supongamos que invocamos el método `findAll` de `Direccion`. Las consultas sql que se lanzan a la base de datos serían estas:

```sql
Hibernate: 
    select
        direccion0_.id as id1_1_,
        direccion0_.calle as calle2_1_,
        direccion0_.ciudad_id as ciudad_i6_1_,
        direccion0_.codigo_postal as codigo_p3_1_,
        direccion0_.piso as piso4_1_,
        direccion0_.tipo as tipo5_1_,
        direccion0_.usuario_id as usuario_7_1_ 
    from
        direccion direccion0_
Hibernate: 
    select
        ciudad0_.id as id1_0_0_,
        ciudad0_.nombre as nombre2_0_0_ 
    from
        ciudad ciudad0_ 
    where
        ciudad0_.id=?
Hibernate: 
    select
        usuario0_.id as id1_2_0_,
        usuario0_.email as email2_2_0_,
        usuario0_.nombre as nombre3_2_0_,
        usuario0_.password as password4_2_0_,
        usuario0_.telefono as telefono5_2_0_ 
    from
        usuario usuario0_ 
    where
        usuario0_.id=?
Hibernate: 
    select
        ciudad0_.id as id1_0_0_,
        ciudad0_.nombre as nombre2_0_0_ 
    from
        ciudad ciudad0_ 
    where
        ciudad0_.id=?
Hibernate: 
    select
        usuario0_.id as id1_2_0_,
        usuario0_.email as email2_2_0_,
        usuario0_.nombre as nombre3_2_0_,
        usuario0_.password as password4_2_0_,
        usuario0_.telefono as telefono5_2_0_ 
    from
        usuario usuario0_ 
    where
        usuario0_.id=?
```

¿Qué ha sucedido aquí?

- `findAll` de todas las direcciones devuelven `direccion1`, `direccion2` y `direccion3`.
- Para `direccion1`, se busca la ciudad referenciada, y devuelve `ciudad1`.
- Para `direccion1`, se busca el usuario referenciado, y devuelve `usuario1`.
- Para `direccion2`, la ciudad referenciada ya está almacenada en el _contexto de persistencia_, por lo que no es necesario rescatarla. Se busca entonces el usuario referenciado, y devuelve `usuario2`.
- Para `direccion3`, el usuario referenciado ya está en el contexto de persitencia, y entonces se busca la ciudad, devolviendo `ciudad2`.

Todo este comportamiento sucede gracias al mecanismo de caché de primer nivel. Si una entidad está en estado _managed_, el `EntityManager` la mantiene en caché, por lo que no se vuelve a rescatar de la base de datos.

Pero, ¿por qué hay tantas llamadas a la base de datos? Esto, a todas luces, se podría haber resuelto con una única consulta, algo más compleja. El problema reside en el tipo de _fetching_. Las anotaciones `@OneToOne` y `@ManyToOne`, por defecto, tienen configuración `FetchType.EAGER`. Por tanto, cuando se rescata una dirección de la base de datos, JPA rescata las entidades asociadas.

Si tenemos un gran número de filas en cada tabla la lista de llamadas individuales puede ser tremendamente larga, y **esto supone un grave problema de rendimiento**. La estrategia `EAGER` provoca un problema llamado el `problema N+1`, que significa que la invocación de 1 sentencia _select_ genera otras N sentencias select más. Es por esto que **es recomendable utilizar el mecanismo LAZY**.

Si actualizamos la entidad `Direccion` para usar esta estrategia, quedaría:

```java
@Entity
@NoArgsConstructor @AllArgsConstructor @Builder
@Getter @Setter
public class Direccion {

    @Id
    @GeneratedValue
    private Long id;

    private String tipo, calle, piso, codigoPostal;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ciudad_id")
    private Ciudad ciudad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;


}
```

Ahora, si invocamos el método `findAll`, el código sql resultante sería:

 ```sql
 Hibernate: 
    select
        direccion0_.id as id1_1_,
        direccion0_.calle as calle2_1_,
        direccion0_.ciudad_id as ciudad_i6_1_,
        direccion0_.codigo_postal as codigo_p3_1_,
        direccion0_.piso as piso4_1_,
        direccion0_.tipo as tipo5_1_,
        direccion0_.usuario_id as usuario_7_1_ 
    from
        direccion direccion0_
```

El objeto `Direccion` que hemos obtenido contiene _proxies_ (no los objetos referenciados) a las entidades `Ciudad` y `Usuario`. Si tratamos de acceder a la ciudad o el usuario, (y siempre y cuando la sesión esté abierta), se ejecutarán las sentencias a la base de datos. Y por tanto, el problema `N+1` volverá a aparecer de nuevo.

Otra dificultad estriba en que en ocasiones nos puede interesar obtener las direcciones con sus referencias de forma _perezosa_, pero en otras ocasiones, con la información del usuario, o de la ciudad, o de ambas. Por tanto, establecer la estrategia de _fetching_ a nivel de entidad no nos aporta flexibilidad en diferentes situaciones.

Todo esto lo podemos solucionar con los **grafos de entidad**.

## La solución

Una de las cosas que podrían mejorar el rendimiento y solventar nuestro anterior problema sería el uso de consultas JOIN cuando tratamos de rescatar los objetos referenciados. Podríamos escribir unas cuantas consultas JPQL para ello:

```java
@Query("""
    SELECT d FROM Direccion d
    JOIN FETCH d.usuario usuario
    JOIN FETCH d.ciudad ciudad
""")
List<Direccion> findAllDirecciones();
```

Aunque esto es una solución aceptable en cuanto a rendimiento por la consulta ejecutada, en un proyecto empresarial a gran escala es seguro que el número de métodos como el anterior puede crecer de forma exponencial. En estos casos, los **grafos de entidad** se tornan una mejor solución.

## Grafos de entidad (_Entity Graphs_)

Los grafos de entidad fueron introducidos en JPA 2.1 y son usados para permitir un fetching más dinámico y parcial. Cuando una entidad tiene referencias a otras entidades podemos especificar el _fetch plan_ mediante grafos de entidad para establecer qué atributos o propiedades se tomarán juntos. Para ello se utiliza la anotación `@NamedEntityGraph` a nivel de clase.

```java
@NamedEntityGraph(
        name = "grafo-direccion-ciudad-usuario",
        attributeNodes = {
                @NamedAttributeNode("ciudad"),
                @NamedAttributeNode("usuario")
        }
)
@Entity
@NoArgsConstructor @AllArgsConstructor @Builder
@Getter @Setter
public class Direccion {

    @Id
    @GeneratedValue
    private Long id;

    private String tipo, calle, piso, codigoPostal;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ciudad_id")
    private Ciudad ciudad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;


}
```

Con una estrategia **estática** de tipo LAZY, podemos definir un grafo de entidad, llamado `grafo-direccion-ciudad-usuario`. Este grafo de entidad contiene los atributos ciudad y el usuario, por tanto, estos objetos serán tomados de la base de datos conjuntamente con la direccion. **Si queremos obtener estos atributos, el método `findAll` ya no nos sirve, puesto que ejecutaría el _fetch plan_ de tipo LAZY.** Para asignar el grafo de entidad con nombre a una consulta tenemos dos estrategias:

- Spring Data JPA: hacerlo a través de la anotación `@EntityGraph` en los repositorios.
- JPA: hacerlo programáticamente a través de `EntityManager`.

Probemos la primera opción:

```java
public interface DireccionRepository extends JpaRepository<Direccion, Long> {

    @EntityGraph("grafo-direccion-ciudad-usuario")
    List<Direccion> findByIdNotNull();

}
```

> **En el repositorio, no podemos sobrescribir el método `findAll`. Sin embargo, un método equivalente sería el anterior, porque buscaría todas aquellas direcciones que no tienen el atributo Id nulo (no puede haber ninguna puesto que es el atributo identificador).


El resultado sería:

```sql
Hibernate: 
    select
        direccion0_.id as id1_1_0_,
        ciudad1_.id as id1_0_1_,
        usuario2_.id as id1_2_2_,
        direccion0_.calle as calle2_1_0_,
        direccion0_.ciudad_id as ciudad_i6_1_0_,
        direccion0_.codigo_postal as codigo_p3_1_0_,
        direccion0_.piso as piso4_1_0_,
        direccion0_.tipo as tipo5_1_0_,
        direccion0_.usuario_id as usuario_7_1_0_,
        ciudad1_.nombre as nombre2_0_1_,
        usuario2_.email as email2_2_2_,
        usuario2_.nombre as nombre3_2_2_,
        usuario2_.password as password4_2_2_,
        usuario2_.telefono as telefono5_2_2_ 
    from
        direccion direccion0_ 
    left outer join
        ciudad ciudad1_ 
            on direccion0_.ciudad_id=ciudad1_.id 
    left outer join
        usuario usuario2_ 
            on direccion0_.usuario_id=usuario2_.id 
    where
        direccion0_.id is not null
```

Podemos comprobar como, en lugar de realizar `n+1` consultas, automáticamente ha generado una consulta de tipo JOIN.

Se puede tener más de un método diferente de consulta con el mismo grafo de entidad. Por ejemplo, para buscar las direcciones de un usuario o de una ciudad.

```java
public interface DireccionRepository extends JpaRepository<Direccion, Long> {

    @EntityGraph("grafo-direccion-ciudad-usuario")
    List<Direccion> findByIdNotNull();
    
    @EntityGraph("grafo-direccion-ciudad-usuario")
    List<Direccion> findByUsuarioId(Long id);
}
```

También podemos definir múltiples grafos de entidad con nombre para una única entidad.

```java
@NamedEntityGraphs({
        @NamedEntityGraph(
                name = "grafo-direccion-ciudad-usuario",
                attributeNodes = {
                        @NamedAttributeNode("ciudad"),
                        @NamedAttributeNode("usuario")
                }
        ),
        @NamedEntityGraph(
                name = "grafo-direccion-ciudad",
                attributeNodes = {
                        @NamedAttributeNode("ciudad"),
                }
        ),
        @NamedEntityGraph(
                name = "grafo-direccion-usuario",
                attributeNodes = {
                        @NamedAttributeNode("usuario")
                }
        ),

})
@Entity
@NoArgsConstructor @AllArgsConstructor @Builder
@Getter @Setter
public class Direccion { // resto del código }
```

### Tipos de grafos de entidad

La anotación `@EntityGraph` tiene un parámetro `type` que puede tomar dos valores:

- `FETCH`. Es el valor por defecto. Cuando es elegido, los atributos indicados como nodos se tratan con el fetching de tipo `EAGER`, y los atributos que no están especificados como nodos, se tratan con fetching de tipo `LAZY`.
- `LOAD`. Cuando este tipo es seleccionado, los atributos especificados como nodos se tratan con fetching de tipo `EAGER`, y a los que no lo están se les aplica el fetching que tengan definido, o el tipo de fetching por defecto para esa asociación.

### Subgrafos

Podemos llevar el uso de los grafos de entidad un poco más allá, a través de los subgrafos.

Hasta ahora, nuestro tratamiento de asociaciones ha sido unidireccional. Para demostrar el uso de subgrafos, vamos a hacer la asociación entre `Usuario` y `Direccion` bidireccional, y vamos a añadir una nueva entidad llamada `Grupo`, que podrá agrupar un conjunto de usuarios.

```java
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Grupo {


    @Id
    @GeneratedValue
    private Long id;

    String nombre;

    @OneToMany(mappedBy = "grupo", fetch = FetchType.LAZY)
    private List<Usuario> usuarios = new ArrayList<>();


}

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Usuario {

    @Id
    @GeneratedValue
    private Long id;

    private String nombre, email, password, telefono;

    @OneToMany(mappedBy = "direccion", fetch = FetchType.LAZY)
    private List<Direccion> direcciones = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private Grupo grupo;


    public void addToGrupo(Grupo g) {
        grupo = g;
        g.getUsuarios().add(this);
    }

    public void removeFromGrupo(Grupo g) {
        grupo = null;
        g.getUsuarios().remove(this);
    }


}

@Entity
@NoArgsConstructor @AllArgsConstructor @Builder
@Getter @Setter
public class Direccion {

    @Id
    @GeneratedValue
    private Long id;

    private String tipo, calle, piso, codigoPostal;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ciudad_id")
    private Ciudad ciudad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    public void addToUsuario(Usuario u) {
        usuario = u;
        u.getDirecciones().add(this);
    }

    public void removeFromUsuario(Usuario u) {
        u.getDirecciones().remove(this);
        usuario = null;
    }


}

```

Vamos ahora a tratar de definir un grafo que obtenga un grupo con sus usuarios, y que estos contengan sus direcciones, y que las direcciones contengan la información de la ciudad. Vamos, un árbol como el siguiente:

![Grafo con subgrafos](./grafo2.png)

El grafo de entidad puede contener subgrafos, y los subgrafos son definidos con la anotación `@NamedSubgraph`. Esta anotación también usa `@NamedAttributeNode` para especificar qué atributos se van a tomar de la base de datos.

```java
@NamedEntityGraphs(
        @NamedEntityGraph(
                name = "grafo-con-usuario-y-subgrafo",
                attributeNodes = {
                        @NamedAttributeNode( value = "usuarios", subgraph = "subgrafo-usuario") },
                subgraphs = {
                        @NamedSubgraph(
                                name = "subgrafo-usuario",
                                attributeNodes = { @NamedAttributeNode( value = "direcciones", subgraph = "subgrafo-direcciones") }
                        ),
                        @NamedSubgraph(
                                name = "subgrafo-direcciones",
                                attributeNodes = {
                                        @NamedAttributeNode("ciudad")
                                }
                        )
                }
        )
)
@Entity
@NoArgsConstructor  @AllArgsConstructor @Builder
@Getter @Setter
public class Grupo {


    @Id
    @GeneratedValue
    private Long id;

    String nombre;

    @Builder.Default
    @OneToMany(mappedBy = "grupo", fetch = FetchType.LAZY)
    //private List<Usuario> usuarios = new ArrayList<>();
    Set<Usuario> usuarios = new HashSet<>();


}

```

> El uso de `Set` como colección para una asociación entre entidades es controvertido, y muchos autores lo desaconsejan.



De esta forma, podríamos obtener todo con una sola consulta (Grupo -> Usuarios -> Direcciones -> Ciudades)


## Grafos de entidad "sin nombre"

Al igual que sucede con las consultas, no todos los grafos de entidad tienen que estar definidos como `@NamedEntityGraph`. Spring Data JPA nos permite definir grafos de entidad directamente en una consulta, a través de la anotación `@EntityGraph` en el repositorio. Para ello, tenemos la propiedad `attributePaths`, que nos permite indicar el atributo que se quiere conectar a través del grafo.

```java
public interface GrupoRepository extends JpaRepository<Grupo, Long> {

   // Resto de métodos

    @EntityGraph(attributePaths = {"usuarios"})
    List<Grupo> findByNombre(String nombre);

}

```

La ejecución de esta consulta produce el siguiente código sql:

```sql
    select
        grupo0_.id as id1_2_0_,
        usuarios1_.id as id1_3_1_,
        grupo0_.nombre as nombre2_2_0_,
        usuarios1_.email as email2_3_1_,
        usuarios1_.grupo_id as grupo_id6_3_1_,
        usuarios1_.nombre as nombre3_3_1_,
        usuarios1_.password as password4_3_1_,
        usuarios1_.telefono as telefono5_3_1_,
        usuarios1_.grupo_id as grupo_id6_3_0__,
        usuarios1_.id as id1_3_0__ 
    from
        grupo grupo0_ 
    left outer join
        usuario usuarios1_ 
            on grupo0_.id=usuarios1_.grupo_id 
    where
        grupo0_.nombre=?

```

Sin en lugar de realizarlo con Spring Data JPA, queremos bajar un nivel la abstracción para hacerlo con JPA, podríamos implementarlo a nivel de la clase servicio, mediante una consulta, y haciendo uso del método `EntityManager.createEntityGraph`. Esto nos permitirá construir un objeto de tipo `EntityGraph<Entity>`.

A la hora de ejecutar consultas, el método `EntityManager.find()` es capaz de recibir como argumento una colección de propiedades, entre las cuales podemos incluir el grafo de entidad.


# Bibliografía

- https://turkogluc.com/understanding-jpa-entity-graphs/
- https://turkogluc.com/understanding-the-effective-data-fetching-with-jpa-entity-graphs-part-2/
- https://vladmihalcea.com/jpa-entity-graph/ 
