package com.salesianostriana.dam.springdata.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Curso {

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