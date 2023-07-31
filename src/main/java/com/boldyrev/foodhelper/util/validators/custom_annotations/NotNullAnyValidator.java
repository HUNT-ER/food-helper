package com.boldyrev.foodhelper.util.validators.custom_annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

public class NotNullAnyValidator implements ConstraintValidator<NotNullAny, Object> {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void initialize(NotNullAny constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        try {
            for (Field field : o.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                if (field.get(o) != null) {
                    return true;
                }
            }
        } catch (Exception e) {
            log.warn("NotNullAny validation has errors");
            System.out.println("ERROR");
            return false;
        }
        return false;
    }
}
