package org.example.IO;

import org.example.IO.exceptions.NoSuchTaskException;
import org.example.model.Task;

import java.util.List;

public interface DataStorage {
    void saveTask(Task task);
    void deleteTask(Task id);
    List<Task> getAllTasks();
    Task getTask(String id) throws NoSuchTaskException;
    String getNewId();
}
