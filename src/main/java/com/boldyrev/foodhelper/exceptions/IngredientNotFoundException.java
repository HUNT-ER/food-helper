package com.boldyrev.foodhelper.exceptions;

public class IngredientNotFoundException extends RuntimeException {

    public IngredientNotFoundException(String message) {
        super(message);
    }
}
