package com.salesianostriana.dam.springdata.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter @Setter
@AllArgsConstructor @Builder
@EntityListeners(AuditingEntityListener.class)
public class Alumno implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String nombre, apellidos, email;

    private LocalDate fechaNacimiento;

    private LocalDateTime fechaMatricula;

    @CreatedDate
    private LocalDateTime fechaCreacion;

    @LastModifiedDate
    private LocalDateTime fechaUltimaEdicion;


    @OrderBy
    @Column(name="orden")
    private int order;

}
