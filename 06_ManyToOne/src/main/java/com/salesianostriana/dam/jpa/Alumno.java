package com.salesianostriana.dam.jpa;


import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Builder
@Getter @Setter
@ToString
public class Alumno {

    @Id
    @GeneratedValue
    private Long id;

    private String nombre, apellidos, email;

    @ManyToOne
    @JoinColumn(name = "curso_id", foreignKey = @ForeignKey(name = "FK_ALUMNO_CURSO"))
    private Curso curso;


}
