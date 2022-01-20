package com.salesianostriana.dam.validacion.password;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class StrongPasswordValidator
            implements ConstraintValidator<StrongPassword, String> {

    int min, max;
    boolean hasUpper, hasLower, hasNumber, hasAlpha, hasOther;


    @Override
    public void initialize(StrongPassword constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
        this.hasLower = constraintAnnotation.hasLower();
        this.hasUpper = constraintAnnotation.hasUpper();
        this.hasAlpha = constraintAnnotation.hasAlpha();
        this.hasNumber = constraintAnnotation.hasNumber();
        this.hasOther = constraintAnnotation.hasOthers();
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        return false;
    }
}
