package com.salesianostriana.dam.springdata;

import com.salesianostriana.dam.springdata.model.Alumno;
import com.salesianostriana.dam.springdata.model.Monumento;
import com.salesianostriana.dam.springdata.repos.MonumentoRepository;
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
	public CommandLineRunner test(MonumentoRepository monumentoRepository) {
		return args -> {


			/*monumentoRepository.save(nuevoMonumento);

			System.out.printf("%s - %s - %s\n", nuevoMonumento.getId().toString(), nuevoMonumento.getNombre(), nuevoMonumento.getDescripcion());*/


		};
	}




}


