package com.salesianostriana.dam;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

public class AppTest {

    static Calculadora c;

    static int a, b;

    @BeforeAll
    static void init() {
        c = new Calculadora();
    }

    @BeforeEach
    void saludar() {
        a = ThreadLocalRandom.current().nextInt();
        b = ThreadLocalRandom.current().nextInt();
        //System.out.printf("%d, %d\n", a, b);
    }

    @Test
    @DisplayName("2 + 2  = 4")
    public void sumarDosEnteros() {
        assertEquals(a+b, c.sumar(a,b));
    }

    @Test
    public void dividirDosNumeros() {
        assertEquals(a/b, c.divisionEntera(a,b));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    public void divideEntre0(int argument) {
        assertThrows(ArithmeticException.class, () -> {
            c.divisionEntera(argument,0);
        });
    }


    @ParameterizedTest
    @ValueSource(strings = {"Ola k ase", "Mercadona o ke ase", "Yo nací en una bañera que no tenía tapón"})
    @NullAndEmptySource
    public void testComparacionCadena(String argument) {
        String cadena = "Ola k ase";
        assertNotEquals(cadena, argument);
    }

    @ParameterizedTest
    @EnumSource(ChronoUnit.class)
    void testWithEnumSource(TemporalUnit unit) {
        assertNotNull(unit);
    }

    @ParameterizedTest
    @CsvSource({
            "1,2,3",
            "2,3,5"
    })
    public void testSuma(int a, int b, int result) {
        assertEquals(result, c.sumar(a,b));

    }

    @ParameterizedTest
    @MethodSource("range")
    public void testSumaMethod(int argument) {
        assertThrows(ArithmeticException.class, () -> {
            c.divisionEntera(argument,0);
        });
    }

    static IntStream range() {
        return new Random().ints().limit(50);
    }



}
