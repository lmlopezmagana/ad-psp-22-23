package com.salesianostriana.dam;

import com.salesianostriana.dam.model.Admin;
import com.salesianostriana.dam.model.Cliente;
import com.salesianostriana.dam.service.AdminService;
import com.salesianostriana.dam.service.ClienteService;
import com.salesianostriana.dam.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MainDePrueba {

    private final UsuarioService usuarioService;
    private final AdminService adminService;
    private final ClienteService clienteService;


    @PostConstruct
    public void test() {

        // Insertamos algunos datos.

        Cliente c1 = Cliente.builder()
                .username("cliente1")
                .password("12345")
                .fullname("Cliente Uno")
                .build();


        Cliente c2 = Cliente.builder()
                .username("cliente2")
                .password("12345")
                .fullname("Cliente Dos")
                .build();

        Cliente c3 = Cliente.builder()
                .username("admin")
                .password("12345")
                .fullname("Cliente Que Se Cree Admin")
                .build();

        clienteService.saveAll(List.of(c1, c2));

        Admin admin = Admin.builder()
                .username("admin")
                .password("strong")
                .fullname("The Real Admin")
                .build();

        adminService.save(admin);

        // Obtenemos los administradores por username;

        Admin queried = adminService.findByUsername("admin");

        if (queried != null)
            System.out.printf("%s - %s\n\n", queried.getFullname(), queried.getUsername());
        else
            System.out.println("No encontrado");


        // Obtenemos todos los usuarios

        usuarioService.findThemAll().forEach(u -> System.out.printf("%s - %s\n", u.getFullname(), u.getUsername()));


    }

}
