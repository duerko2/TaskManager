package org.example;

import org.example.IO.Output;
import org.example.model.Task;

import java.util.List;

public class TaskDisplayService {
    private DisplayStrategy displayStrategy;

    TaskDisplayService(Output output) {
        this.displayStrategy = new StandardDisplayStrategy(output);
    }
    public void displayTask(Task task) {
        displayStrategy.display(task);
    }
    public void displayTaskList(List<Task> tasks) {
        for (Task task : tasks) {
            displayStrategy.display(task);
        }
    }

    public void setDisplayStrategy(DisplayStrategy displayStrategy) {
        this.displayStrategy = displayStrategy;
    }
}
