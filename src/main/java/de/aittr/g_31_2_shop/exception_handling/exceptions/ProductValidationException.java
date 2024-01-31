package de.aittr.g_31_2_shop.exception_handling.exceptions;

public class ProductValidationException extends RuntimeException{
    public ProductValidationException(String message) {
        super(message);
    }
}
