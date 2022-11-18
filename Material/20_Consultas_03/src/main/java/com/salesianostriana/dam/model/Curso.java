package com.salesianostriana.dam.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NamedEntityGraph(
        name = Curso.CURSO_CON_ALUMNOS,
        attributeNodes = {
                @NamedAttributeNode("alumnos")
        }

)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Curso {

    public static final String CURSO_CON_ALUMNOS = "grafo-curso-con-alumnos";

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    private String nombre;

    private String tutor;

    @Builder.Default
    @OneToMany(mappedBy="curso")
    private List<Alumno> alumnos = new ArrayList<>();
}
