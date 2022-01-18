package com.salesianostriana.dam.users.controllers;

import com.salesianostriana.dam.users.dto.CreateUserDto;
import com.salesianostriana.dam.users.dto.GetUserDto;
import com.salesianostriana.dam.users.dto.UserDtoConverter;
import com.salesianostriana.dam.users.services.UserEntityService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth/register")
@RequiredArgsConstructor
public class UserController { 
	
	private final UserEntityService userEntityService;
	private final UserDtoConverter userDtoConverter;
	
	
	@PostMapping("/")
	public GetUserDto nuevoUsuario(@RequestBody CreateUserDto newUser) {
			return userDtoConverter.convertUserEntityToGetUserDto(userEntityService.nuevoUsuario(newUser));

	}

}
