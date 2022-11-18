package com.salesianostriana.dam.entitygraph.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Ciudad {

    @Id
    @GeneratedValue
    private Long id;

    private String nombre;
}
