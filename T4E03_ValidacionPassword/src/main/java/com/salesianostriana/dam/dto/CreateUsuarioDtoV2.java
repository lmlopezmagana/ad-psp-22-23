package com.salesianostriana.dam.dto;

import com.salesianostriana.dam.validacion.multiple.anotaciones.FieldsValueMatch;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor @AllArgsConstructor
@FieldsValueMatch.List({
        @FieldsValueMatch(
                field = "password",
                fieldMatch = "verifyPassword",
                message = "Las contraseñas no coinciden"
        ),
        @FieldsValueMatch(
                field = "email",
                fieldMatch = "verifyEmail",
                message = "Las direcciones de correo electrónico no coinciden"
        )
})
public class CreateUsuarioDtoV2 {

    private String login;
    @Email
    private String email;
    @Email
    private String verifyEmail;
    @NotEmpty
    private String password;
    private String verifyPassword;

}
