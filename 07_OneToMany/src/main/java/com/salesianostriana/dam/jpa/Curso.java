package com.salesianostriana.dam.jpa;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Curso {

    @Id @GeneratedValue
    private Long id;

    private String nombre, tutor;

    @ToString.Exclude
    @OneToMany(mappedBy = "curso", fetch = FetchType.EAGER)
    @Builder.Default
    private List<Alumno> alumnos = new ArrayList<>();


    @PreRemove
    public void setNullAlumnos() {
        alumnos.forEach(a -> a.setCurso(null));
    }



}
