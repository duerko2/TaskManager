package org.example.IO;

import org.example.IO.exceptions.CouldNotSaveTaskException;
import org.example.IO.exceptions.NoSuchTaskException;
import org.example.model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryDataStorage implements DataStorage{
    private Map<String, Task> taskMap;

    public InMemoryDataStorage() {
        taskMap = new HashMap<>();
    }

    @Override
    public void saveTask(Task task) {
        taskMap.put(task.getId(), task);
    }

    @Override
    public void deleteTask(Task task) {
        taskMap.remove(task.getId());
    }

    @Override
    public List<Task> getAllTasks() {
        return new ArrayList<>(taskMap.values());
    }

    @Override
    public Task getTask(String id) throws NoSuchTaskException {
        Task task = taskMap.get(id);
        if (task == null) {
            throw new NoSuchTaskException("No task with id: " + id);
        }
        return task;
    }
    @Override
    public String getNewId() {
        return String.valueOf(taskMap.size() + 1);
    }

}
