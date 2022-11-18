package com.salesianostriana.dam.herencia;

import com.salesianostriana.dam.herencia.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MainDePrueba {

    private final ClienteRepository clienteRepository;
    private final PedidoRepository pedidoRepository;

    @PostConstruct
    public void test() {

        Cliente normal = Cliente.builder()
                .nombre("normal")
                .apellidos("normal normal")
                .email("normal@clientes.com")
                .build();

        Cliente particular = ClienteParticular.builder()
                .nombre("Sr. Cliente")
                .apellidos("don Particular")
                .email("elmasparticulardelos@clientes.com")
                .puntosAcumulados(100)
                .build();

        Cliente corporativo = ClienteCorporativo.builder()
                .nombreEmpresa("La Empresa")
                .nombre("Representante de")
                .apellidos("La Empresa")
                .email("corporativo@clientes.com")
                .build();

        clienteRepository.saveAll(List.of(normal, particular, corporativo));

        clienteRepository.listarClientesNormales()
                .forEach(c -> System.out.println(c.getNombre()));

        clienteRepository.listarClientesCorporativo()
                .forEach(c -> System.out.println(c.getNombre()));


        Pedido p1 = Pedido.builder()
                .estado(EstadoPedido.CREADO)
                .build();

        Pedido p2 = Pedido.builder()
                .estado(EstadoPedido.EN_PROCESO)
                .build();

        p1.addToCliente(particular);
        p2.addToCliente(particular);

        pedidoRepository.saveAll(List.of(p1, p2));


        clienteRepository.findAll();

        clienteRepository.findById(2l);


    }
}
