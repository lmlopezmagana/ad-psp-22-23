package com.salesianostriana.dam.springdata.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class NotasPK implements Serializable {

    private Long alumno_id;

    private Long asignatura_id;

}
