package com.salesianostriana.dam.security.jwt;

import com.salesianostriana.dam.users.model.UserEntity;
import com.salesianostriana.dam.users.services.UserEntityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Log
@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final UserEntityService userService;
    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 1. Obtener el token de la petición (request)
        String token = getJwtFromRequest(request);

        // 2. Validar token
        try {
        if (StringUtils.hasText(token) && jwtProvider.validateToken(token)) {

            //Long userId = jwtProvider.getUserIdFromJwt(token);
            UUID userId = jwtProvider.getUserIdFromJwt(token);

            Optional<UserEntity> userEntity = userService.findById(userId);

            if (userEntity.isPresent()) {
                UserEntity user = userEntity.get();
                UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        user,
                        user.getRole(),
                        user.getAuthorities()
                );
                authentication.setDetails(new WebAuthenticationDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);


            }
        }

        } catch (Exception ex) {
            // Informar en el log
            log.info("No se ha podido establecer el contexto de seguridad (" + ex.getMessage() + ")");
        }

        filterChain.doFilter(request, response);
        // 2.1 Si es válido, autenticamos al usuario

        // 2.2 Si no es válido, lanzamos una excepcion



    }

    private String getJwtFromRequest(HttpServletRequest request) {
        // Authorization: Bearer eltoken.qiemas.megusta
        String bearerToken = request.getHeader(JwtProvider.TOKEN_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(JwtProvider.TOKEN_PREFIX)) {
            return bearerToken.substring(JwtProvider.TOKEN_PREFIX.length());
        }
        return null;
    }

}
