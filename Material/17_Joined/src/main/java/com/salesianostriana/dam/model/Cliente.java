package com.salesianostriana.dam.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
public class Cliente {

    @Id @GeneratedValue
    private Long id;

    private String email, nombre, apellidos;

}
