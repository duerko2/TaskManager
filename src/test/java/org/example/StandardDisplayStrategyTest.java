package org.example;

import junit.framework.TestCase;
import org.example.IO.Output;
import org.example.model.Category;
import org.example.model.Priority;
import org.example.model.Status;
import org.example.model.Task;
import org.junit.Before;
import org.mockito.Mock;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class StandardDisplayStrategyTest extends TestCase {
    StandardDisplayStrategy standardDisplayStrategy;
    @Mock
    private Output output;

    @Before
    public void setUp() {
        output = mock(Output.class);
        standardDisplayStrategy = new StandardDisplayStrategy(output);
    }


    public void testDisplay() {
        Task task = createSampleTask("1");

        standardDisplayStrategy.display(task);

        verify(output).print("Task: Sample Task Name\n" +
                "Description: Sample Task\n" +
                "Category: WORK\n" +
                "Priority: LOW\n" +
                "Status: CREATED\n" +
                "Due: 21/1/2024\n" +
                "Created: 31/12/2023\n" +
                "Updated: null"
        );
    }

    private Task createSampleTask(String id) {
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
                    null
            );
        } catch (ParseException e) {
            // Handle parsing exception if needed
            return null;
        }
    }
}