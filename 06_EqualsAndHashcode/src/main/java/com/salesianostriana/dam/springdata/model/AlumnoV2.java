package com.salesianostriana.dam.springdata.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.UUID;

/**
 * EJEMPLO 1
 * No implementamos equals y hashCode y delegamos en la clase Object de Java
 *
 * Podemos tener penalización si unamos colecciones de tipo Map o Set
 *
 */
@Entity
//@NoArgsConstructor
@Getter @Setter
@AllArgsConstructor @Builder
public class AlumnoV2 implements Serializable {

    @Id
    private UUID id;

    private String nombre, apellidos, email;

    public AlumnoV2() {
        id = UUID.randomUUID();
    }


}
