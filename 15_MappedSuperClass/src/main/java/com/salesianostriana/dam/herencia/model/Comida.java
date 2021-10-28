package com.salesianostriana.dam.herencia.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;

@Entity
@Getter @Setter
@SuperBuilder
@NoArgsConstructor @AllArgsConstructor
public class Comida extends Producto {

    private boolean esFrio;

    private String tipo;





}
