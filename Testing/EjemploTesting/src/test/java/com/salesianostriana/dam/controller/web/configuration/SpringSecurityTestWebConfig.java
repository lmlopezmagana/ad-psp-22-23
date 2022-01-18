package com.salesianostriana.dam.controller.web.configuration;

import com.salesianostriana.dam.users.model.UserEntity;
import com.salesianostriana.dam.users.model.UserRole;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.List;
import java.util.Set;

@TestConfiguration
public class SpringSecurityTestWebConfig {

    @Bean("customUserDetailsService")
    @Primary
    public UserDetailsService userDetailsService() {

        UserEntity admin = UserEntity.builder()
                .username("admin")
                .password("admin")
                .roles(Set.of(UserRole.ADMIN))
                .build();

        UserEntity user = UserEntity.builder()
                .username("user")
                .password("user")
                .roles(Set.of(UserRole.USER))
                .build();


        return new InMemoryUserDetailsManager(List.of(
            admin, user
        ));

    }


}
