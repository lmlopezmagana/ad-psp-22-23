package com.salesianostriana.dam.herencia.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Entity
@Getter @Setter
@SuperBuilder @AllArgsConstructor @NoArgsConstructor
@DiscriminatorValue("CC")
public class ClienteCorporativo extends Cliente {

    private String nombreEmpresa;
    private String telefono;


    @ManyToOne
    private Empleado empleado;

    // Helpers Empleado - ClienteCorporativo

    public void addToEmpleado(Empleado e) {
        empleado = e;
        e.getClientes().add(this);
    }

    public void removeFromEmpleado(Empleado e) {
        e.getClientes().remove(this);
        empleado = null;
    }


}
