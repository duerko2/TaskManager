package org.example.IO;

import org.example.IO.exceptions.NoSuchTaskException;
import org.example.model.Category;
import org.example.model.Priority;
import org.example.model.Status;
import org.example.model.Task;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;


public class InMemoryDataStorageTest {
    private InMemoryDataStorage dataStorage;

    @BeforeEach
    public void setUp() {
        dataStorage = new InMemoryDataStorage();
    }

    @Test
    public void saveTask() {
        Task task = createSampleTask("1");

        dataStorage.saveTask(task);

        Task retrievedTask = dataStorage.getAllTasks().get(0);
        assertEquals(task, retrievedTask);
    }

    @Test
    public void deleteTask() {
        Task task = createSampleTask("1");
        dataStorage.saveTask(task);

        dataStorage.deleteTask(task);

        assertTrue(dataStorage.getAllTasks().isEmpty());
    }

    @Test
    public void getTask() throws NoSuchTaskException {
        Task task = createSampleTask("1");
        dataStorage.saveTask(task);

        Task retrievedTask = dataStorage.getTask("1");

        assertEquals(task, retrievedTask);
    }

    @Test
    public void getTaskNoSuchTask() throws NoSuchTaskException {
        assertThrows(NoSuchTaskException.class, () -> {
            dataStorage.getTask("2"); // Task with ID "2" does not exist, so an exception should be thrown
        });
    }

    @Test
    public void getNewId() {
        assertEquals("1", dataStorage.getNewId());

        // Save a task and check the new ID
        Task task = createSampleTask("1");
        dataStorage.saveTask(task);

        assertEquals("2", dataStorage.getNewId());
    }

    private Task createSampleTask(String id) {
        try {
            Date date = new SimpleDateFormat("dd/MM/yyyy").parse("31/12/2023");
            return new Task(
                    Category.WORK,
                    "Sample Task",
                    id,
                    "Sample Task Name",
                    Status.CREATED,
                    Priority.LOW,
                    date,
                    new Date(),
                    null
            );
        } catch (ParseException e) {
            // Handle parsing exception if needed
            return null;
        }
    }
}
