package org.example;

import org.example.IO.DataStorage;
import org.example.IO.Input;
import org.example.IO.exceptions.NoSuchTaskException;
import org.example.IO.Output;
import org.example.model.Category;
import org.example.model.Priority;
import org.example.model.Status;
import org.example.model.Task;
import org.example.wrongInputExceptions.WrongCategoryError;
import org.example.wrongInputExceptions.WrongFieldError;
import org.example.wrongInputExceptions.WrongPriorityError;
import org.example.wrongInputExceptions.WrongStatusError;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TaskService {
    DataStorage dataStorage;
    Input input;
    Output output;

    public TaskService(DataStorage dataStorage, Input input, Output output) {
        this.dataStorage = dataStorage;
        this.input = input;
        this.output = output;
    }

    public void createTask() throws WrongCategoryError, WrongPriorityError, ParseException {
        // get input from user create the task and then save to data storage
        String name = getTaskName();
        String description = getTaskDescription();
        Category category = getTaskCategory();
        Priority priority = getTaskPriority();
        Date date = getTaskDueDate();

        Task task = new Task(
                category,
                description,
                dataStorage.getNewId(),
                name,
                Status.CREATED,
                priority,
                date,
                new Date(),
                null
        );

        dataStorage.saveTask(task);
        output.print("Task created successfully with id:" + task.getId());
    }



    public void deleteTask() {
        // get input from user delete the task and then save to data storage
        Task task;
        try {
            task = getTask();
        } catch (NoSuchTaskException e) {
            output.print(e.getMessage());
            return;
        }
        dataStorage.deleteTask(task);
    }
    public void updateTask() {
        try {
        Task task = getUpdatedTaskFromUser();
        if (task == null) {
            return;
        }
        dataStorage.saveTask(task);
        output.print("Task updated successfully");
        } catch (WrongCategoryError wrongCategoryError) {
            output.print("Invalid category");
        } catch (WrongStatusError wrongStatusError) {
            output.print("Invalid status");
        } catch (WrongPriorityError wrongPriorityError) {
            output.print("Invalid priority");
        } catch (WrongFieldError wrongFieldError) {
            output.print("Invalid field");
        } catch (ParseException e) {
            output.print("Invalid date format");
        }
    }



    public Task getTask() throws NoSuchTaskException {
        output.print("Enter task id");
        String id = input.getInput();
        return dataStorage.getTask(id);
    }
    public List<Task> getAllTasks() {
        return dataStorage.getAllTasks();
    }
    private Task getUpdatedTaskFromUser() throws WrongCategoryError, WrongStatusError, WrongPriorityError, WrongFieldError, ParseException {
        Task task;
        try {
            task = getTask();
        } catch (NoSuchTaskException e) {
            output.print(e.getMessage());
            // if task is not found try again
            return getUpdatedTaskFromUser();
        }

        output.print("Enter the field you want to update");
        output.print("1: Name, 2: Description, 3: Category, 4: Priority, 5: Status, 6: Due Date, 7: Cancel");
        String field = input.getInput();
        switch (field) {
            case "1":
                String name = getTaskName();
                task.setName(name);
                break;
            case "2":
                String description = getTaskDescription();
                task.setDescription(description);
                break;
            case "3":
                Category category = getTaskCategory();
                task.setCategory(category);
                break;
            case "4":
                Priority priority = getTaskPriority();
                task.setPriority(priority);
                break;
            case "5":
                Status status = getTaskStatus();
                if (status == null)
                    return null;
                task.setStatus(status);
                break;
            case "6":
                Date date = getTaskDueDate();
                task.setDueDate(date);
                break;
            case "7":
                return null;
            default:
                throw new WrongFieldError("Invalid field");

        }
        task.setUpdatedDate(new Date());
        return task;
    }

    private Status getTaskStatus() throws WrongStatusError{
        output.print("Enter status");
        output.print("1: CREATED, 2: IN_PROGRESS, 3: DONE, 4: Cancel");
        String statusString = input.getInput();
        Status status;
        switch (statusString) {
            case "1":
                status = Status.CREATED;
                break;
            case "2":
                status = Status.IN_PROGRESS;
                break;
            case "3":
                status = Status.DONE;
                break;
            case "4":
                return null;
            default:
                throw new WrongStatusError("Invalid status");
        }
        return status;
    }


    private Date getTaskDueDate() throws ParseException{
        output.print("Enter task due date in dd/MM/yyyy format");
        String dueDate = input.getInput();
        return new SimpleDateFormat("dd/MM/yyyy").parse(dueDate);
    }


    private Priority getTaskPriority() throws WrongPriorityError{
        output.print("Enter task priority");
        output.print("1: LOW, 2: MEDIUM, 3: HIGH");
        String priorityString = input.getInput();
        Priority priority;
        switch(priorityString){
            case "1":
                priority = Priority.LOW;
                break;
            case "2":
                priority = Priority.MEDIUM;
                break;
            case "3":
                priority = Priority.HIGH;
                break;
            default:
                throw new WrongPriorityError("Invalid priority");
        }
        return priority;
    }

    private Category getTaskCategory() throws WrongCategoryError {
        output.print("Enter number corresponding to correct category");
        output.print("1: WORK, 2: PERSONAL, 3: STUDY, 4: HEALTH,");
        output.print("5: SHOPPING, 6: SOCIAL, 7: TRAVEL, 8:FINANCIAL");
        output.print("9: ENTERTAINMENT, 10: OTHER");
        String categoryString = input.getInput();
        Category category;
        switch(categoryString){
            case "1":
                category = Category.WORK;
                break;
            case "2":
                category = Category.PERSONAL;
                break;
            case "3":
                category = Category.STUDY;
                break;
            case "4":
                category = Category.HEALTH;
                break;
            case "5":
                category = Category.SHOPPING;
                break;
            case "6":
                category = Category.SOCIAL;
                break;
            case "7":
                category = Category.TRAVEL;
                break;
            case "8":
                category = Category.FINANCIAL;
                break;
            case "9":
                category = Category.ENTERTAINMENT;
                break;
            case"10":
                category = Category.OTHER;
                break;
            default:
                throw new WrongCategoryError("Invalid category");
        }
        return category;
    }

    private String getTaskDescription() {
        output.print("Enter task description");
        return input.getInput();
    }

    private String getTaskName() {
        output.print("Enter task name");
        return input.getInput();
    }
}
