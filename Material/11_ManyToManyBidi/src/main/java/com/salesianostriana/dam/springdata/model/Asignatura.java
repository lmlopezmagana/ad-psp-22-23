package com.salesianostriana.dam.springdata.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

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
