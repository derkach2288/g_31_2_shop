package de.aittr.g_31_2_shop.exception_handling.exceptions;

public class InactiveProductException extends RuntimeException{
    public InactiveProductException(String message) {
        super(message);
    }
}
