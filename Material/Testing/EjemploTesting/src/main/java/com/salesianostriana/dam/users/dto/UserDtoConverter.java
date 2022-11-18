package com.salesianostriana.dam.users.dto;

import java.util.stream.Collectors;

import com.salesianostriana.dam.users.model.UserRole;
import org.springframework.stereotype.Component;

import com.salesianostriana.dam.users.model.UserEntity;

@Component
public class UserDtoConverter {
	
	public GetUserDto convertUserEntityToGetUserDto(UserEntity user) {
		return GetUserDto.builder()
				.username(user.getUsername())
				.avatar(user.getAvatar())
				.fullName(user.getFullName())
				.email(user.getEmail())
				.roles(user.getRoles().stream()
							.map(UserRole::name)
							.collect(Collectors.toSet())
				).build();
	}

}
