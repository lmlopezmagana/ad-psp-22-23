
# Ejemplo 11 - Ejemplo una asociación _Many To Many_ (bidireccional)

Partimos desde el ejemplo anterior

## ¿Cuál es nuestro modelo de datos?

![diagrama uml](./uml.jpg) 

## Tratamiento bidireccional de una asociación `@ManyToMany`

El tratamiento _bidireccional_ de una asociación nos permite utilizar dicha asociación desde las dos entidades que están conectadas. Partimos del código del ejemplo anterior:

```java
@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Curso implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String nombre;
    private String tutor;

    @OneToMany(mappedBy = "curso")
    private List<Alumno> alumnos = new ArrayList<>();

    public Curso(String nombre, String tutor) {
        this.nombre = nombre;
        this.tutor = tutor;
    }
}

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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(joinColumns = @JoinColumn(name = "alumno_id",
                              foreignKey = @ForeignKey(name="FK_MATRICULA_ALUMNO")),
            inverseJoinColumns = @JoinColumn(name = "asignatura_id",
                    foreignKey = @ForeignKey(name="FK_MATRICULA_ASIGNATURA")),
            name = "matriculaciones"
    )
    private List<Asignatura> asignaturas = new ArrayList<>();


    ///////////////////////////////////////
    /* HELPERS de la asociación con curso*/
    ///////////////////////////////////////

    public void addCurso(Curso c) {
        this.curso = c;
        if (c.getAlumnos() == null)
            c.setAlumnos(new ArrayList<>());
        c.getAlumnos().add(this);
    }

    public void removeCurso(Curso c) {
        c.getAlumnos().remove(this);
        this.curso = null;
    }

}

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Asignatura implements Serializable {

    @Id @GeneratedValue
    private Long id;

    private String nombre, profesor;


}


```


## Paso 1: Modificar la entidad `Asignatura`

```java
@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Asignatura implements Serializable {

    @Id @GeneratedValue
    private Long id;

    private String nombre, profesor;

    @ManyToMany(mappedBy = "asignaturas", fetch = FetchType.EAGER)
    private List<Alumno> alumnos;


}
```

## Paso 2: Modificar la entidad `Alumno`

```java
@Entity
@NoArgsConstructor
@Getter @Setter
@AllArgsConstructor @Builder
public class Alumno implements Serializable {

    // Resto de atributos

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(joinColumns = @JoinColumn(name = "alumno_id",
                              foreignKey = @ForeignKey(name="FK_MATRICULA_ALUMNO")),
            inverseJoinColumns = @JoinColumn(name = "asignatura_id",
                    foreignKey = @ForeignKey(name="FK_MATRICULA_ASIGNATURA")),
            name = "matriculaciones"
    )
    private List<Asignatura> asignaturas = new ArrayList<>();


    // Resto de métodos

    ////////////////////////////////////////////
    /* HELPERS de la asociación con Asignatura*/
    ////////////////////////////////////////////

    public void addAsignatura(Asignatura a) {
        if (this.getAsignaturas() == null)
            this.setAsignaturas(new ArrayList<>());
        this.getAsignaturas().add(a);

        if (a.getAlumnos() == null)
            a.setAlumnos(new ArrayList<>());
        a.getAlumnos().add(this);
    }

    public void removeAsignatura(Asignatura a) {
        a.getAlumnos().remove(this);
        this.getAsignaturas().remove(a);
    }



}
```

Si ejecutamos el proyecto, podemos apreciar el siguiente DDL generado:

```sql
   create table alumno (
       id bigint not null,
        apellidos varchar(255),
        email varchar(255),
        nombre varchar(255),
        curso_id bigint,
        primary key (id)
    )

    
    create table alumno_asignaturas (
       alumno_id bigint not null,
        asignatura_id bigint not null
    )

    
    create table asignatura (
       id bigint not null,
        nombre varchar(255),
        profesor varchar(255),
        curso_id bigint,
        primary key (id)
    )

    
    create table curso (
       id bigint not null,
        nombre varchar(255),
        tutor varchar(255),
        primary key (id)
    )

    
    alter table alumno 
       add constraint FKojks48ahsqwkx9o2s7pl0212p 
       foreign key (curso_id) 
       references curso

    
    alter table alumno_asignaturas 
       add constraint FKrukf4f8fa1cexj0cwh1m7kpim 
       foreign key (asignatura_id) 
       references asignatura

    
    alter table alumno_asignaturas 
       add constraint FKrt0id7bs1lp5qkuyhw9ceustn 
       foreign key (alumno_id) 
       references alumno

    
    alter table asignatura 
       add constraint FKr7icgav26emducg973dp80fga 
       foreign key (curso_id) 
       references curso
```

## Paso 3: Utilizar esta asociación

En este caso, siendo bidireccional, la tenemos que manejar a través de los métodos helpers.

```java
Asignatura asignatura = Asignatura.builder()
                .nombre("AD")
                .profesor("Luismi")
                .alumnos(new ArrayList<>())
                .build();
        asignaturaService.save(asignatura);

        List<Alumno> nuevosAlumnos = List.of(
          Alumno.builder().nombre("Alejandro").asignaturas(new ArrayList<>()).build(),
          Alumno.builder().nombre("Cynthia").asignaturas(new ArrayList<>()).build(),
          Alumno.builder().nombre("Pablo").asignaturas(new ArrayList<>()).build(),
          Alumno.builder().nombre("Manuel").asignaturas(new ArrayList<>()).build()
        );

        nuevosAlumnos.forEach(nuevoAlumno -> nuevoAlumno.addAsignatura(asignatura));

        alumnoService.saveAll(nuevosAlumnos);
```

