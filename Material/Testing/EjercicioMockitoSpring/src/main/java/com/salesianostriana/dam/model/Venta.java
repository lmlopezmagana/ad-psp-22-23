package com.salesianostriana.dam.model;

import lombok.*;
import org.springframework.data.repository.cdi.Eager;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Venta {


    @Id @GeneratedValue
    private Long id;

    @Builder.Default
    private LocalDate fecha = LocalDate.now();

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @Builder.Default
    private List<LineaDeVenta> lineasDeVenta = new ArrayList<>();

    @ManyToOne
    private Cliente cliente;

    public void addLineaVenta(LineaDeVenta lineaDeVenta) {
        lineaDeVenta.setVenta(this);
        lineasDeVenta.add(lineaDeVenta);
    }

    public void removeLineaVenta(LineaDeVenta lineaDeVenta) {
        lineaDeVenta.setVenta(null);
        lineasDeVenta.remove(lineaDeVenta);
    }
}
