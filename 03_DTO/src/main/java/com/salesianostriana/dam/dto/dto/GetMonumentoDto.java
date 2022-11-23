package com.salesianostriana.dam.dto.dto;

import com.salesianostriana.dam.dto.model.Monumento;
import lombok.*;

@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class GetMonumentoDto /*MonumentoResponse*/ {

    private String nombre;
    private String loc;
    private String ubicacion;
    private String categoria;

    public static GetMonumentoDto of (Monumento m) {
        return GetMonumentoDto
                .builder()
                .nombre(m.getNombre())
                .loc(m.getLoc())
                .ubicacion(String.format("%s (%s)", m.getCiudad(), m.getPais()))
                .categoria(m.getCategoria().getNombre())
                .build();
    }

}
