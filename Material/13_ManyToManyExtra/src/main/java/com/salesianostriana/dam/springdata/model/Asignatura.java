package com.salesianostriana.dam.springdata.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Asignatura implements Serializable {

    @Id @GeneratedValue
    private Long id;

    private String nombre, profesor;

    /*
    @Builder.Default
    @ManyToMany(mappedBy = "asignaturas", fetch = FetchType.EAGER)
    private List<Alumno> alumnos = new ArrayList<>();
    */

    @Builder.Default
    @OneToMany(mappedBy = "asignatura")
    private List<Notas> notas = new ArrayList<>();

    @ManyToOne
    private Curso curso;

    // HELPERS con Curso

    public void addToCurso(Curso c) {
        curso = c;
        c.getAsignaturas().add(this);
    }

    public void removeFromCurso(Curso c) {
        c.getAsignaturas().remove(this);
        curso = null;
    }



}
