package org.example.IO;

import org.example.IO.exceptions.CouldNotDeleteTaskException;
import org.example.IO.exceptions.CouldNotSaveTaskException;
import org.example.IO.exceptions.NoSuchTaskException;
import org.example.model.Category;
import org.example.model.Priority;
import org.example.model.Status;
import org.example.model.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SQLLiteDataStorageTest {

    private SQLLiteDataStorage dataStorage;

    @BeforeEach
    public void setUp() {
        try {
            dataStorage = new SQLLiteDataStorage("jdbc:sqlite:sample.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    public void tearDown() {
        try {
            List<Task> tasks = dataStorage.getAllTasks();
            for (Task task : tasks) {
                dataStorage.deleteTask(task);
            }
        } catch (NoSuchTaskException e) {
            throw new RuntimeException(e);
        } catch (CouldNotDeleteTaskException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testSaveTask() throws CouldNotSaveTaskException, NoSuchTaskException {
        Task saveTask = createSampleTask();
        dataStorage.saveTask(saveTask);
        Task retrieveTask = dataStorage.getTask(saveTask.getId());
        assertEquals(saveTask,retrieveTask);
    }

    public void testDeleteTask() {
    }

    public void testGetAllTasks() {
    }

    public void testGetTask() {
    }

    public void testGetNewId() {
    }

    private Task createSampleTask() {
        String id = dataStorage.getNewId();

        try {
            Date createdDate = new SimpleDateFormat("dd/MM/yyyy").parse("31/12/2023");
            Date dueDate = new SimpleDateFormat("dd/MM/yyyy").parse("21/01/2024");
            return new Task(
                    Category.WORK,
                    "Sample Task",
                    id,
                    "Sample Task Name",
                    Status.CREATED,
                    Priority.LOW,
                    dueDate,
                    createdDate,
                    createdDate
            );
        } catch (ParseException e) {
            // Handle parsing exception if needed
            return null;
        }
    }
}