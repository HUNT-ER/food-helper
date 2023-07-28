package com.boldyrev.foodhelper.util.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public abstract class CustomValidator implements Validator {

    protected final String entityClass;

    protected CustomValidator(String entityClass) {
        this.entityClass = entityClass;
    }

    protected String getErrors(Errors errors) {
        StringBuilder builder = new StringBuilder();

        builder.append(entityClass)
                .append(" not saved: ");

        errors.getFieldErrors().forEach(e ->
            builder.append(e.getField())
                .append(" - ")
                .append(e.getDefaultMessage())
                .append("; "));

        return builder.toString();
    }


}
