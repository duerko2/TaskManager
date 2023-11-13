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
import org.junit.jupiter.params.ParameterizedTest;


import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
    public void testSaveAndRetrieveTask() throws CouldNotSaveTaskException, NoSuchTaskException {
        Task saveTask = createSampleTask();
        dataStorage.saveTask(saveTask);
        Task retrieveTask = dataStorage.getTask(saveTask.getId());
        assertEquals(saveTask,retrieveTask);
    }

    @Test
    public void testNewIDAlwaysStartsAsOne(){
        String id = dataStorage.getNewId();
        assertEquals("1",id);
    }

    @Test
    public void testNewIDAfterInsertion() {
        Task saveTask = createSampleTask();
        assertDoesNotThrow( () -> {
            dataStorage.saveTask(saveTask);
        });
        String id = dataStorage.getNewId();
        assertEquals("2",id);
    }

    @Test
    public void testNewIDAfterMultipleInsertions() {
        for(int i = 0; i < 10; i++){
            Task saveTask = createSampleTask();
            assertDoesNotThrow( () -> {
                dataStorage.saveTask(saveTask);
            });
        }
        String id = dataStorage.getNewId();
        assertEquals("11",id);
    }

    @Test
    public void testNewIDAfterMixOfInsertionsAndDeletions() {
        assertDoesNotThrow( () -> {
            for (int i = 0; i < 10; i++) {
                Task saveTask = createSampleTask();
                dataStorage.saveTask(saveTask);
            }
            for (int i = 0; i < 5; i++) {
                dataStorage.deleteTask(dataStorage.getTask(String.valueOf(i + 1)));
            }
            for (int i = 0; i < 10; i++) {
                Task saveTask = createSampleTask();
                dataStorage.saveTask(saveTask);
            }
            for (int i = 10; i < 15; i++) {
                dataStorage.deleteTask(dataStorage.getTask(String.valueOf(i + 1)));
            }
        }
        );
        String id = dataStorage.getNewId();
        assertEquals("21",id);
    }

    @Test
    public void testCannotInsertSameIDTwice() {
        Task saveTask = createSampleTask();

        // First does not throw exception
        assertDoesNotThrow( () -> {
            dataStorage.saveTask(saveTask);
        });

        // Second throws exception
        assertThrows(CouldNotSaveTaskException.class, () -> {
            dataStorage.saveTask(saveTask);
        });

        saveTask.setId("2");
        // No exception with new ID
        assertDoesNotThrow( () -> {
            dataStorage.saveTask(saveTask);
        });
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