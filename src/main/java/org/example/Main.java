package org.example;

import org.example.IO.*;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {

        Output output = new StdOutput();
        Input input = new StdInput();
        DataStorage dataStorage = null;
        try {
            dataStorage = new SQLLiteDataStorage("jdbc:sqlite:src/main/resources/database.db");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        TaskService taskService = new TaskService(dataStorage, input, output);


        App app = new App(taskService, output, input, new TaskDisplayService(output));
        app.startApp();

    }
}