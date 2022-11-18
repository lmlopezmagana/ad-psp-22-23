package com.salesianostriana.dam;

import com.salesianostriana.dam.upload.StorageService;
import com.salesianostriana.dam.users.model.UserEntity;
import com.salesianostriana.dam.users.repos.UserEntityRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.stream.Collectors;


@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public CommandLineRunner init(StorageService storageService, PasswordEncoder passwordEncoder, UserEntityRepository repository) {
		return args -> {
			// Inicializamos el servicio de ficheros
			storageService.deleteAll();
			storageService.init();


/*			repository.saveAll(repository.findAll().stream()
					.map(u -> {
						String password = StringUtils.capitalize(u.getUsername()+"1");
						System.out.println(password);
						u.setPassword(passwordEncoder.encode(password));
						return u;
					})
					.collect(Collectors.toList()));
*/
			
			
		};

	}

}
