package org.example;

import org.example.IO.Input;
import org.example.IO.Output;

public class App {
    TaskService taskService;
    Output output;
    Input input;

    public App(TaskService taskService, Output output, Input input) {
        this.taskService = taskService;
        this.output = output;
        this.input = input;
    }

    public void startApp() {
        while(true) {
            output.print("1. Add task");
            output.print("2. Delete task");
            output.print("3. Update task");
            output.print("4. Get task");
            output.print("5. Get all tasks");
            output.print("6. Exit");
            String in = input.getInput();
            switch (in) {
                case "1":
                    taskService.createTask();
                    break;
                case "2":
                    taskService.deleteTask();
                    break;
                case "3":
                    taskService.updateTask();
                    break;
                case "4":
                    taskService.getTask("1");
                    break;
                case "5":
                    taskService.getAllTasks();
                    break;
                case "6":
                    closeApp();
                    break;
                default:
                    output.print("Invalid input");
            }
        }

    }
    public void closeApp(){

    }

}
