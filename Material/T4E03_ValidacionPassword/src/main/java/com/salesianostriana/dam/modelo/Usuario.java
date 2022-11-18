package com.salesianostriana.dam.modelo;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Builder
public class Usuario {

    @Id @GeneratedValue
    private Long id;

    private String login;

    private String email;

    private String password;

    private LocalDateTime createdAt;

}
