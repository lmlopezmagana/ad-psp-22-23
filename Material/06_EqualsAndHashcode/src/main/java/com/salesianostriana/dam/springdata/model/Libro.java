package com.salesianostriana.dam.springdata.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

/**
 * EJEMPLO 2
 *
 * Entidad que tiene un atributo natural que permite diferenciar a una instancia de otra
 * ¡OJO! Debemos garantizar que este valor no cambia una vez seteado, o tendremos dificultades.
 *
 * Podemos hacer uso de @NaturalId (anotación de Hibernate)
 *
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Libro {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    @NaturalId
    private String isbn;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Libro libro = (Libro) o;
        return Objects.equals(getIsbn(), libro.getIsbn());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIsbn());
    }

    /*
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book book = (Book) o;
        return Objects.equals(getIsbn(), book.getIsbn());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIsbn());
    }*/

}
