package com.salesianostriana.dam.herencia.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@NamedEntityGraph(
        name = Empleado.EMPLEADO_CLIENTE_CORP,
        attributeNodes = {
                @NamedAttributeNode("clientes")
        }
)
@Entity
@Getter @Setter
@Builder
@NoArgsConstructor @AllArgsConstructor
public class Empleado implements Serializable {

    public static final String EMPLEADO_CLIENTE_CORP = "grafo-empleado-clientecorporativo";

    @Id @GeneratedValue
    private Long id;

    private String nombre, apellidos, email;

    @Builder.Default
    @OneToMany(mappedBy = "empleado")
    private List<ClienteCorporativo> clientes = new ArrayList<>();
}
