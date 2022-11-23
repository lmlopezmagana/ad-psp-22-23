package com.salesianostriana.dam.dto.dto;

import com.salesianostriana.dam.dto.model.Monumento;
import org.springframework.stereotype.Component;

@Component
public class MonumentoDtoConverter {

    public Monumento createMonumentoDtoToMonumento(CreateMonumentoDto c) {
        return new Monumento(
                c.getCodigoPais(),
                c.getPais(),
                c.getCiudad(),
                c.getLoc(),
                c.getNombre(),
                c.getDescripcion(),
                c.getUrlImagen()
        );
    }

    public GetMonumentoDto monumentoToGetMonumentoDto(Monumento m) {
        return GetMonumentoDto
                .builder()
                .nombre(m.getNombre())
                .loc(m.getLoc())
                .ubicacion(String.format("%s (%s)", m.getCiudad(), m.getPais()))
                .categoria(m.getCategoria().getNombre())
                .build();

        /*GetMonumentoDto result = new GetMonumentoDto();
        result.setNombre(m.getNombre());
        result.setLoc(m.getLoc());
        result.setUbicacion(String.format("%s (%s)", m.getCiudad(), m.getPais()));
        result.setCategoria(m.getCategoria().getNombre());
        return result;*/



    }


}
