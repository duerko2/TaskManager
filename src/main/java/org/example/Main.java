package org.example;

import org.example.IO.*;

public class Main {
    public static void main(String[] args) {

        Output output = new StdOutput();
        Input input = new StdInput();
        DataStorage dataStorage = new InMemoryDataStorage();
        TaskService taskService = new TaskService(dataStorage, input, output);


        App app = new App(taskService, output, input, new TaskDisplayService(output));
        app.startApp();

    }
}