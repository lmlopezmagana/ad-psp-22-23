package com.salesianostriana.dam.herencia.model;

import com.salesianostriana.dam.herencia.model.Cliente;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
@SuperBuilder @AllArgsConstructor @NoArgsConstructor
@DiscriminatorValue("CP")
public class ClienteParticular extends Cliente {

    private int puntosAcumulados;

}
