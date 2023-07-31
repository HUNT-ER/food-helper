package com.boldyrev.foodhelper.util.validators.custom_annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotNullAnyValidator.class)
public @interface NotNullAny {
    String message() default "At least one property must be not null";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
