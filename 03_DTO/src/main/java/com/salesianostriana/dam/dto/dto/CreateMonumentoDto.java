package com.salesianostriana.dam.dto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class CreateMonumentoDto /*MonumentoRequest*/ {

    private String codigoPais;
    private String pais;
    private String ciudad;
    private String loc;
    private String nombre;
    private String descripcion;
    private String urlImagen;
    private Long categoriaId;




}
