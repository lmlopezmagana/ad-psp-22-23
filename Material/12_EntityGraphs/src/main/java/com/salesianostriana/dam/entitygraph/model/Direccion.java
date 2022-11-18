package com.salesianostriana.dam.entitygraph.model;

import lombok.*;

import javax.persistence.*;


@NamedEntityGraphs({
        @NamedEntityGraph(
                name = "grafo-direccion-ciudad-usuario",
                attributeNodes = {
                        @NamedAttributeNode("ciudad"),
                        @NamedAttributeNode("usuario")
                }
        ),
        @NamedEntityGraph(
                name = "grafo-direccion-ciudad",
                attributeNodes = {
                        @NamedAttributeNode("ciudad"),
                }
        ),
        @NamedEntityGraph(
                name = "grafo-direccion-usuario",
                attributeNodes = {
                        @NamedAttributeNode("usuario")
                }
        ),

})
@Entity
@NoArgsConstructor @AllArgsConstructor @Builder
@Getter @Setter
public class Direccion {

    @Id
    @GeneratedValue
    private Long id;

    private String tipo, calle, piso, codigoPostal;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ciudad_id")
    private Ciudad ciudad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    public void addToUsuario(Usuario u) {
        usuario = u;
        u.getDirecciones().add(this);
    }

    public void removeFromUsuario(Usuario u) {
        u.getDirecciones().remove(this);
        usuario = null;
    }


}
