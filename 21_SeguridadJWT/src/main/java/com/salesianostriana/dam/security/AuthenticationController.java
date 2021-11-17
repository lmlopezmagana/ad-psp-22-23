package com.salesianostriana.dam.security;

import com.salesianostriana.dam.users.model.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {

        Authentication authentication =
                authenticationManager.authenticate(
                   new UsernamePasswordAuthenticationToken(
                           loginDto.getEmail(),
                           loginDto.getPassword()
                   )
                );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Devolver una respuesta adecuada
        // que incluya el token del usuario.
        String jwt = jwtProvider.generateToken(authentication);


        UserEntity user = (UserEntity) authentication.getPrincipal();

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                       JwtUserResponse.builder()
                               .fullName(user.getFullName())
                               .email(user.getEmail())
                               .avatar(user.getAvatar())
                               .role(user.getRole().name())
                               .token(jwt)
                               .build()

                );

    }

}
