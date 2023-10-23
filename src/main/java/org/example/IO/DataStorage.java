package org.example.IO;

import org.example.IO.exceptions.CouldNotDeleteTaskException;
import org.example.IO.exceptions.CouldNotSaveTaskException;
import org.example.IO.exceptions.NoSuchTaskException;
import org.example.model.Task;

import java.util.List;

public interface DataStorage {
    void saveTask(Task task) throws CouldNotSaveTaskException;
    void deleteTask(Task id) throws CouldNotDeleteTaskException;
    List<Task> getAllTasks() throws NoSuchTaskException;
    Task getTask(String id) throws NoSuchTaskException;
    String getNewId();
}
