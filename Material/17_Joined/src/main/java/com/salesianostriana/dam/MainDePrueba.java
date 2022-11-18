package com.salesianostriana.dam;

import com.salesianostriana.dam.model.Cliente;
import com.salesianostriana.dam.model.ClienteVip;
import com.salesianostriana.dam.repos.ClienteRepository;
import com.salesianostriana.dam.repos.ClienteVipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class MainDePrueba {

    private final ClienteRepository clienteRepository;
    private final ClienteVipRepository clienteVipRepository;

    @PostConstruct
    public void test() {

        Cliente c1 = Cliente.builder()
                .nombre("Cliente 1")
                .apellidos("Apellido 1 Apellido 2")
                .email("cliente@gmail.com")
                .build();

        clienteRepository.save(c1);

        ClienteVip vip = ClienteVip.builder()
                .nombre("Cliente Vip 1")
                .apellidos("Vip Vip")
                .email("vip@gmail.com")
                .fechaVip(LocalDate.now())
                .build();

        //clienteVipRepository.save(vip);

        // Ojo, con el repositorio de cliente también lo podríamos insertar
        clienteRepository.save(vip);

        // Obtener todos los clientes, vip o no.
        System.out.println("TODOS LOS CLIENTES");
        clienteRepository.findAll().forEach(c -> System.out.println(c.getNombre()));

        System.out.println("");

        // Obtener los no VIP
        System.out.println("TODOS LOS CLIENTES NO VIP");
        clienteRepository.clientesNoVip().forEach(c -> System.out.println(c.getNombre()));

        System.out.println("");

        // Obtener los VIP
        System.out.println("TODOS LOS CLIENTES VIP");
        clienteVipRepository.findAll().forEach(c -> System.out.println(c.getNombre()));



    }

}
