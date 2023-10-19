package org.example;

import org.example.IO.Input;
import org.example.IO.Output;

import java.util.List;

public class TaskService {
    DataStorage dataStorage;
    Input input;
    Output output;

    public TaskService(DataStorage dataStorage, Input input, Output output) {
        this.dataStorage = dataStorage;
        this.input = input;
        this.output = output;
    }

    public void createTask() {
        // get input from user create the task and then save to data storage
        output.print("Enter task name");
        String name = input.getInput();
        output.print("Enter task description");
        String description = input.getInput();
        Task task = new Task(name, description);


        dataStorage.saveTask();
    }
    public void deleteTask() {
        // get input from user delete the task and then save to data storage

        dataStorage.deleteTask(task);
    }
    public void updateTask(Task task) {
        dataStorage.saveTask(task);
    }
    public Task getTask(String id) {
        return dataStorage.loadTask(id);
    }
    public List<Task> getAllTasks() {
        return dataStorage.getAllTasks();
    }
}
