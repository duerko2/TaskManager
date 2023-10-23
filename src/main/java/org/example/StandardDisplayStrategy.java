package org.example;

import org.example.IO.Output;
import org.example.model.Task;

import java.time.LocalDate;

public class StandardDisplayStrategy implements DisplayStrategy {
    private Output output;

    public StandardDisplayStrategy(Output output) {
        this.output = output;
    }

    @Override
    public void display(Task task) {
        LocalDate updatedDate = task.getUpdatedDate() == null ? null : task.getUpdatedDate().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
        LocalDate createdDate =  task.getCreatedDate().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
        LocalDate dueDate = task.getDueDate().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();

        String createdDateOutput = createdDate.getDayOfMonth()+"/"+createdDate.getMonth().getValue()+"/"+createdDate.getYear();
        String dueDateOutput = dueDate.getDayOfMonth()+"/"+dueDate.getMonth().getValue()+"/"+dueDate.getYear();
        String updatedDateOutput = updatedDate == null ? "null" : updatedDate.getDayOfMonth()+"/"+updatedDate.getMonth().getValue()+"/"+updatedDate.getYear();
        output.print("Task: " + task.getName() + "\n" +
                "Description: " + task.getDescription() + "\n" +
                "Category: " + task.getCategory() + "\n" +
                "Priority: " + task.getPriority() + "\n" +
                "Status: " + task.getStatus() + "\n" +
                "Due: " + dueDateOutput + "\n" +
                "Created: " + createdDateOutput + "\n" +
                "Updated: " + updatedDateOutput
        );
    }

    /*
    verify(output).print("Task: Sample Task\n" +
                "Description: Sample Task Name\n" +
                "Category: WORK\n" +
                "Priority: LOW\n" +
                "Status: CREATED\n" +
                "Due Date: 31/12/2023\n" +
                "Created Date: 31/12/2020\n" +
                "Completed Date: null\n"
        );
     */
}
