package com.salesianostriana.dam.validacion.multiple.validadores;

import com.salesianostriana.dam.validacion.multiple.anotaciones.FieldsValueMatch;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.PropertyAccessorFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FieldsValueMatchValidator implements ConstraintValidator<FieldsValueMatch, Object> {

    private String field;
    private String fieldMatch;

    @Override
    public void initialize(FieldsValueMatch constraintAnnotation) {
        this.field = constraintAnnotation.field();
        this.fieldMatch = constraintAnnotation.fieldMatch();

    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        Object fieldValue = PropertyAccessorFactory.forBeanPropertyAccess(value).getPropertyValue(field);
        Object fieldMatchValue = PropertyAccessorFactory.forBeanPropertyAccess(value).getPropertyValue(fieldMatch);

        if (fieldValue != null) {
            return fieldValue.equals(fieldMatchValue);
        } else {
            return fieldMatchValue == null;
        }
    }
}
