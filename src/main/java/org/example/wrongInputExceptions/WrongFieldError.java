package org.example.wrongInputExceptions;

public class WrongFieldError extends Exception {
    public WrongFieldError(String invalid_field) {
        super(invalid_field);
    }
}
