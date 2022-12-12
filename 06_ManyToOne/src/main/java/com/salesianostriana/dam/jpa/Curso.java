package com.salesianostriana.dam.jpa;

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
@ToString
public class Curso {

    @Id @GeneratedValue
    private Long id;

    private String nombre, tutor;


}
