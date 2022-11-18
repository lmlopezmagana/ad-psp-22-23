package com.salesianostriana.dam.herencia.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Pedido implements Serializable {

    @Id @GeneratedValue
    private Long id;

    @CreatedDate
    private LocalDateTime fechaCreacion;

    @Enumerated(EnumType.STRING)
    private EstadoPedido estado;

    @ManyToOne
    private Cliente cliente;

    // Helpers Pedido - Cliente

    public void addToCliente(Cliente c) {
        cliente = c;
        c.getPedidos().add(this);
    }

    public void deleteFromCliente(Cliente c) {
        c.getPedidos().remove(this);
        cliente = null;
    }

}
