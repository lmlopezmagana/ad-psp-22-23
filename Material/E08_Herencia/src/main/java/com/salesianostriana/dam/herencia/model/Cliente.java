package com.salesianostriana.dam.herencia.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@NamedEntityGraph(
        name = Cliente.CLIENTE_PEDIDO,
        attributeNodes = {
                @NamedAttributeNode("pedidos")
        }
)
@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
@DiscriminatorValue("CN")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Cliente implements Serializable {

    public static final String CLIENTE_PEDIDO = "grafo-cliente-pedido";

    @Id
    @GeneratedValue
    protected Long id;

    protected String nombre, apellidos, email;

    protected String direccion, poblacion, cp, provincia;

    @Builder.Default
    @OneToMany(mappedBy = "cliente")
    protected List<Pedido> pedidos = new ArrayList<>();

}
