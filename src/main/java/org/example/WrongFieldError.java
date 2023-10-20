package org.example;

public class WrongFieldError extends Exception {
    public WrongFieldError(String invalid_field) {
        super(invalid_field);
    }
}
