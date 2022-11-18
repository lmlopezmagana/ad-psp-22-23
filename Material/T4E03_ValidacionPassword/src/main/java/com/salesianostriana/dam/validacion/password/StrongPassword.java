package com.salesianostriana.dam.validacion.password;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = StrongPasswordValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface StrongPassword {

    String message() default "";
    Class<?> [] groups() default {};
    Class<? extends Payload> [] payload() default {};

    int min() default 4;
    int max() default 128;

    boolean hasLower() default false;
    boolean hasUpper() default false;

    boolean hasNumber() default false;
    boolean hasAlpha() default false;

    boolean hasOthers() default false;


}
