package com.salesianostriana.dam.modelo;

import lombok.*;
import org.hibernate.validator.constraints.URL;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Builder
public class Producto {

    @Id
    @GeneratedValue
    private Long id;

    //@NotBlank(message = "El nombre del producto no puede estar en blanco")
    @NotBlank(message = "{producto.nombre.blank}")
    private String nombre;

    //@NotNull(message = "El producto debe tener un precio")
    @NotNull(message="{producto.precio.null}")
    //@Min(value = 0, message = "El producto debe tener un precio mayor o igual a 0")
    @Min(value=0, message="{producto.precio.min}")
    private Double precio;

    @Lob
    private String descripcion;

    //@URL
    @URL(message = "{producto.imagen.url}")
    private String imagen;     // URL de la imagen



}
