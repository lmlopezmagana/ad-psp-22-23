package com.salesianostriana.dam.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntegrationTest {

    @Test
    void testEquals() {
        String saludo ="Hola Mundo";
        assertEquals("Hola Mundo", saludo);
    }

}
