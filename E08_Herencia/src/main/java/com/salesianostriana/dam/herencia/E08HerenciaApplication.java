package com.salesianostriana.dam.herencia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class E08HerenciaApplication {

	public static void main(String[] args) {
		SpringApplication.run(E08HerenciaApplication.class, args);
	}

}
