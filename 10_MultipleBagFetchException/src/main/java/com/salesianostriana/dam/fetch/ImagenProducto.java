package com.salesianostriana.dam.fetch;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ImagenProducto {

    @Id
    @GeneratedValue
    private Long id;

    private String url;

    @ManyToOne
    private Producto producto;
}
