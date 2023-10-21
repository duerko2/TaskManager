package org.example;

import org.example.IO.Input;
import org.example.IO.Output;
import org.example.model.Task;
import org.example.wrongInputExceptions.WrongCategoryError;
import org.example.wrongInputExceptions.WrongPriorityError;

import java.text.ParseException;
import java.util.List;

public class App {
    TaskService taskService;
    TaskDisplayService taskDisplayService;
    Output output;
    Input input;

    public App(TaskService taskService, Output output, Input input, TaskDisplayService taskDisplayService) {
        this.taskService = taskService;
        this.output = output;
        this.input = input;
        this.taskDisplayService = taskDisplayService;
    }

    public void startApp() {
        while(true) {
            output.print("1 Add task");
            output.print("2 Delete task");
            output.print("3 Update task");
            output.print("4 Show task");
            output.print("5 Show all tasks");
            output.print("6 Exit");
            String in = input.getInput();
            switch (in) {
                case "1":
                    try {
                        taskService.createTask();
                    } catch (WrongCategoryError e) {
                        output.print(e.getMessage());
                    }  catch (WrongPriorityError e) {
                        output.print(e.getMessage());
                    } catch (ParseException e) {
                        output.print("Wrong date format");
                    }
                    break;
                case "2":
                    taskService.deleteTask();
                    break;
                case "3":
                    taskService.updateTask();
                    break;
                case "4":
                    Task task = taskService.getTask();
                    taskDisplayService.displayTask(task);
                    break;
                case "5":
                    List<Task> tasks = taskService.getAllTasks();
                    taskDisplayService.displayTaskList(tasks);
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
        output.print("Are you sure you want to exit? (y/n)");
        String in = input.getInput();
        if(in.equals("y") || in.equals("Y")) {
            System.exit(0);
        }
    }

}
