package org.example.IO;

public class StdOutput implements Output{
    @Override
    public void print(String message) {
        System.out.println(message);
    }
}
