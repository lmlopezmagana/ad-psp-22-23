package com.salesianostriana.dam.springdata.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Asignatura implements Serializable {

    @Id @GeneratedValue
    private Long id;

    private String nombre, profesor;
}
