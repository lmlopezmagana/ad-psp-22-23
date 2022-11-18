package com.salesianostriana.dam.apirest;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class InitData {

    private final TaskRepository repository;

    @PostConstruct
    public void init() {
        repository.saveAll(
                Arrays.asList(
                    new Task("Rellenar initdata", "Llena de datos el repositorio"),
                        new Task("Cerrar el proyecto", "Antes de lanzar uno nuevo"),
                        new Task("Programar", "Y programar, y programar, y ....")
                )
        );
    }

}
