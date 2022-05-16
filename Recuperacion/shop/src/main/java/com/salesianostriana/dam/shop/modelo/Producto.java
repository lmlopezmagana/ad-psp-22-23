package com.salesianostriana.dam.shop.modelo;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
//@Data Getter+Setter+EqualsHasChode+ToString+RequiredArgsConstructor
// No la usamos para prevenir problemas de relaciones
// circulares cuando hay asociaciones bidireccionales
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Builder
public class Producto {

    @Id
    @GeneratedValue
    private long id;

    private String nombre;

    private double precio;

    private String imagen;

    @ManyToOne
    private Categoria categoria;


}
