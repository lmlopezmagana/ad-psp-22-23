package com.salesianostriana.dam.config.audit;


import com.salesianostriana.dam.users.model.UserEntity;
import lombok.extern.java.Log;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;
import java.util.UUID;

@Log
public class SpringSecurityAuditorAware implements AuditorAware<UUID> {
    @Override
    public Optional<UUID> getCurrentAuditor() {

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            log.info("Principal: " + authentication.getPrincipal());
            UserEntity user = (UserEntity) authentication.getPrincipal();
            return Optional.ofNullable(user.getId());
        } catch (Exception ex) {
            log.info("Error de auditoría: " + ex.getMessage());
        }
        return Optional.empty();

    }
}
