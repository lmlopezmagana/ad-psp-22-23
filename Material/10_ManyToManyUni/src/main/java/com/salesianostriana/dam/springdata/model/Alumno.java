package com.salesianostriana.dam.springdata.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

    @ManyToMany
    @JoinTable(joinColumns = @JoinColumn(name = "alumno_id",
                              foreignKey = @ForeignKey(name="FK_MATRICULA_ALUMNO")),
            inverseJoinColumns = @JoinColumn(name = "asignatura_id",
                    foreignKey = @ForeignKey(name="FK_MATRICULA_ASIGNATURA")),
            name = "matriculaciones"
    )
    private List<Asignatura> asignaturas = new ArrayList<>();



    public void addCurso(Curso c) {
        this.curso = c;
        c.getAlumnos().add(this);
    }

    public void removeCurso(Curso c) {
        c.getAlumnos().remove(this);
        this.curso = null;
    }


}
