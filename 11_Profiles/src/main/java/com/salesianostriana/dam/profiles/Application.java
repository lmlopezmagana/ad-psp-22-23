package com.salesianostriana.dam.profiles;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public CommandLineRunner init(NoteRepository repo) {
		return args -> {
			repo.save(Note.builder()
					.id(1L)
					.title("Vamos a morir todos!!!!")
					.build());
		};
	}

}
