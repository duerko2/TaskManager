package org.example;

import java.util.List;

public interface DataStorage {
    Task loadTask(String id);
    void saveTask(Task task);
    void deleteTask(Task id);
    List<Task> getAllTasks();
    Task getTask(String id);
}
