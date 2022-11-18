package com.salesianostriana.dam.validacion.validadores;

import com.salesianostriana.dam.repositorios.ProductoRepositorio;
import com.salesianostriana.dam.validacion.anotaciones.UniqueName;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueNameValidator implements ConstraintValidator<UniqueName, String> {

    @Autowired
    private ProductoRepositorio repositorio;

    @Override
    public void initialize(UniqueName constraintAnnotation) { }

    @Override
    public boolean isValid(String nombre, ConstraintValidatorContext context) {
        return StringUtils.hasText(nombre) && !repositorio.existsByNombre(nombre);
    }
}

