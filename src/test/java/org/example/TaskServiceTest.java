package org.example;

import org.example.IO.DataStorage;
import org.example.IO.Input;
import org.example.IO.exceptions.CouldNotDeleteTaskException;
import org.example.IO.exceptions.CouldNotSaveTaskException;
import org.example.IO.exceptions.NoSuchTaskException;
import org.example.IO.Output;
import org.example.model.Category;
import org.example.model.Priority;
import org.example.model.Status;
import org.example.model.Task;
import org.example.wrongInputExceptions.WrongCategoryError;
import org.example.wrongInputExceptions.WrongPriorityError;

import org.junit.jupiter.api.*;
import org.mockito.Mock;

import java.text.ParseException;
import java.util.Arrays;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
public class TaskServiceTest {
    private TaskService taskService;

    @Mock
    private DataStorage dataStorage;
    @Mock
    private Input input;
    @Mock
    private Output output;

    @BeforeEach
    public void setUp() {
        dataStorage = mock(DataStorage.class);
        input = mock(Input.class);
        output = mock(Output.class);
        taskService = new TaskService(dataStorage, input, output);
    }

    @Test
    public void testCreateTaskValid() throws WrongCategoryError, WrongPriorityError, ParseException, CouldNotSaveTaskException {
        when(input.getInput()).thenReturn("Sample Task", "Description", "1", "1", "04/14/1995");

        taskService.createTask();

        verify(dataStorage).saveTask(any(Task.class));
    }

    @Test
    public void testCreateTaskInvalidCategory() throws WrongCategoryError, WrongPriorityError, ParseException, CouldNotSaveTaskException {
        when(input.getInput()).thenReturn("Sample Task", "Description", "99", "1", "04/14/1995");


        // Task service throws error when category is invalid
        Assertions.assertThrows(WrongCategoryError.class, () -> {
            taskService.createTask();
        });

        // Verify that the task is not saved
        verify(dataStorage, never()).saveTask(any(Task.class));

    }
    @Test
    public void testCreateTaskInvalidPriority() throws WrongCategoryError, WrongPriorityError, ParseException, CouldNotSaveTaskException {
        when(input.getInput()).thenReturn("Sample Task", "Description", "1", "99", "04/14/1995");

        Assertions.assertThrows(WrongPriorityError.class, () -> {
            taskService.createTask();
        });

        verify(dataStorage, never()).saveTask(any(Task.class));
    }

    // Write similar tests for createTask for other invalid cases
    @Test
    public void testCreateTaskInvalidDate() throws WrongCategoryError, WrongPriorityError, ParseException, CouldNotSaveTaskException {
        when(input.getInput()).thenReturn("Sample Task", "Description", "1", "1", "14-52-1995");

        Assertions.assertThrows(ParseException.class, () -> {
            taskService.createTask();
        });

        verify(dataStorage, never()).saveTask(any(Task.class));
    }

    @Test
    public void testDeleteTask() throws NoSuchTaskException, CouldNotDeleteTaskException {
        Task task = new Task(Category.WORK, "Sample Task","SampleID","Sample Task Name", Status.CREATED, Priority.LOW, null, null, null);
        when(input.getInput()).thenReturn("1");
        when(dataStorage.getTask("1")).thenReturn(task);

        taskService.deleteTask();

        verify(dataStorage).deleteTask(task);
    }

    // Write tests for updateTask and other methods similarly
    @Test
    public void testUpdateTask() throws NoSuchTaskException, CouldNotSaveTaskException {
        Task task = new Task(Category.WORK, "Sample Task","SampleID","Sample Task Name", Status.CREATED, Priority.LOW, null, null, null);
        when(input.getInput()).thenReturn("1");
        when(dataStorage.getTask("1")).thenReturn(task);

        // Check the task name before update
        assertEquals(task.getName(),"Sample Task Name");

        taskService.updateTask();

        // Check that the task has changed name
        assertEquals(task.getName(),"1");

        // Check that the task is saved
        verify(dataStorage).saveTask(task);

        // Check that successful output is printed
        verify(output).print("Task updated successfully");
    }
}
