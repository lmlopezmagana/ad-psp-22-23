package com.salesianostriana.dam.errores.modelo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ApiValidationSubError extends ApiSubError {

    private String objeto;
    private String mensaje;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String campo;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object valorRechazado;


}
