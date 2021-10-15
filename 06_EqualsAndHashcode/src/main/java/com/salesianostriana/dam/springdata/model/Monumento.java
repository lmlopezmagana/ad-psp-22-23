package com.salesianostriana.dam.springdata.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@Builder
public class Monumento {

    @Id @GeneratedValue
    private Long id;

    private String nombre, loc, descripcion;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Monumento monumento = (Monumento) o;
        //return Objects.equals(getId(), monumento.getId());
        return getId() != null && id.equals(monumento.getId());
    }

    /**
     *
     * @return Se devuelve el mismo hashCode para todas las instancias de esta entidad.
     */
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
