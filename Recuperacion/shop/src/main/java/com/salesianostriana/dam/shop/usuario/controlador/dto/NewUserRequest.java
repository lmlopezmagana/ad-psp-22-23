package com.salesianostriana.dam.shop.usuario.controlador.dto;

import lombok.Value;

@Value
public class NewUserRequest {

   private String username;
   private String password;
   private String verifyPassword;

}
