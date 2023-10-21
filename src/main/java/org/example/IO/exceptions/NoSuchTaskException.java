package org.example.IO.exceptions;

public class NoSuchTaskException extends Exception {
    public NoSuchTaskException(String id) {
        super(id);
    }
}
