package org.example.wrongInputExceptions;

public class WrongStatusError extends Exception {
    public WrongStatusError(String invalid_status) {
     super(invalid_status);
    }
}
