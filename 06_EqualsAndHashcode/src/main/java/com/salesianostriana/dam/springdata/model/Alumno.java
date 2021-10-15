package com.salesianostriana.dam.springdata.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import java.io.Serializable;

/**
 * EJEMPLO 1
 * No implementamos equals y hashCode y delegamos en la clase Object de Java
 *
 * Podemos tener penalización si unamos colecciones de tipo Map o Set
 *
 */
@Entity
@NoArgsConstructor
@Getter @Setter
@AllArgsConstructor @Builder
public class Alumno implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String nombre, apellidos, email;


}
