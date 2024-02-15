package de.aittr.g_31_2_shop.exception_handling.exceptions;

public class InactiveCustomerException extends RuntimeException{
    public InactiveCustomerException(String message) {
        super(message);
    }
}
