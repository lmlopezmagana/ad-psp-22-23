package com.salesianostriana.dam.shop.seguridad;


import com.salesianostriana.dam.shop.usuario.servicio.UsuarioServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsService")
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private UsuarioServicio usuarioServicio;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioServicio.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("No hay usuarios con username: " + username));
    }
}
