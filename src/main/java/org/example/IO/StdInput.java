package org.example.IO;

import java.util.Scanner;

public class StdInput implements Input{
    Scanner scanner = new Scanner(System.in);
    @Override
    public String getInput() {
        return scanner.nextLine();
    }

    @Override
    public int getNumber() {
        return scanner.nextInt();
    }
}
