package com.salesianostriana.dam.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@Builder
public class Alumno implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String nombre;
    private String apellido1;
    private String apellido2;
    private String email;
    private LocalDate fechaNacimiento;

    @ManyToOne(fetch = FetchType.LAZY)
    private Curso curso;


    // Helpers Alumno - Curso

    public void addToCurso(Curso c) {
        curso = c;
        c.getAlumnos().add(this);
    }

    public void removeFromCurso(Curso c) {
        c.getAlumnos().remove(this);
        curso = null;
    }


}
