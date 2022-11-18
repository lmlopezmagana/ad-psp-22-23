package com.salesianostriana.dam.herencia;


import com.salesianostriana.dam.herencia.model.Comida;
import com.salesianostriana.dam.herencia.repos.ComidaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class MainDePrueba {

    private final ComidaRepository repository;

    @PostConstruct
    public void test() {

        repository.save(Comida.builder()
                .nombre("Tostá")
                .esFrio(false)
                .tipo("Desayuno")
                .precio(1.3f)
                .build());


    }



}
