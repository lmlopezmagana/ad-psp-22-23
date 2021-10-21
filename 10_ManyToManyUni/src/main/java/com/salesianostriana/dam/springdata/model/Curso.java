package com.salesianostriana.dam.springdata.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
    //@OneToMany(mappedBy = "curso", fetch = FetchType.EAGER)
    private List<Alumno> alumnos = new ArrayList<>();

    public Curso(String nombre, String tutor) {
        this.nombre = nombre;
        this.tutor = tutor;
    }
}