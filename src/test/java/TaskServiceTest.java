
import org.example.*;
import org.example.IO.DataStorage;
import org.example.IO.Input;
import org.example.IO.Output;
import org.example.model.Category;
import org.example.model.Priority;
import org.example.model.Status;
import org.example.model.Task;
import org.example.wrongInputExceptions.WrongCategoryError;
import org.example.wrongInputExceptions.WrongPriorityError;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.text.ParseException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class TaskServiceTest {
    private TaskService taskService;

    @Mock
    private DataStorage dataStorage;
    @Mock
    private Input input;
    @Mock
    private Output output;

    @Before
    public void setUp() {
        dataStorage = mock(DataStorage.class);
        input = mock(Input.class);
        output = mock(Output.class);
        taskService = new TaskService(dataStorage, input, output);
    }

    @Test
    public void testCreateTaskValid() throws WrongCategoryError, WrongPriorityError, ParseException {
        when(input.getInput()).thenReturn("Sample Task", "Description", "1", "1", "04/14/1995");

        taskService.createTask();

        verify(dataStorage).saveTask(any(Task.class));
    }

    @Test(expected = WrongCategoryError.class)
    public void testCreateTaskInvalidCategory() throws WrongCategoryError, WrongPriorityError, ParseException {
        when(input.getInput()).thenReturn("Sample Task", "Description", "99", "1", "04/14/1995");

        taskService.createTask();

        verify(dataStorage, never()).saveTask(any(Task.class));
    }
    @Test(expected = WrongPriorityError.class)
    public void testCreateTaskInvalidPriority() throws WrongCategoryError, WrongPriorityError, ParseException {
        when(input.getInput()).thenReturn("Sample Task", "Description", "1", "99", "04/14/1995");

        taskService.createTask();

        verify(dataStorage, never()).saveTask(any(Task.class));
    }

    // Write similar tests for createTask for other invalid cases
    @Test(expected = ParseException.class)
    public void testCreateTaskInvalidDate() throws WrongCategoryError, WrongPriorityError, ParseException {
        when(input.getInput()).thenReturn("Sample Task", "Description", "1", "1", "14-04-1995");

        taskService.createTask();

        verify(dataStorage, never()).saveTask(any(Task.class));
    }

    @Test
    public void testDeleteTask() {
        Task task = new Task(Category.WORK, "Sample Task","SampleID","Sample Task Name", Status.CREATED, Priority.LOW, null, null, null);
        when(input.getInput()).thenReturn("1");
        when(dataStorage.getTask("1")).thenReturn(task);

        taskService.deleteTask();

        verify(dataStorage).deleteTask(task);
    }

    // Write tests for updateTask and other methods similarly
    @Test
    public void testUpdateTask(){
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
    }
}
