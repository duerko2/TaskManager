package org.example;

import org.example.IO.Input;
import org.example.IO.Output;
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
        output.print("Enter task name");
        String name = input.getInput();
        output.print("Enter task description");
        String description = input.getInput();
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
        Status status = Status.CREATED;
        output.print("Enter task due date in dd/MM/yyyy format");
        String dueDate = input.getInput();
        Date date=new SimpleDateFormat("dd/MM/yyyy").parse(dueDate);
        Task task = new Task(
                category,
                description,
                "1",
                name ,
                status,
                priority,
                date,
                new Date(),
                null);



        dataStorage.saveTask(task);
    }
    public void deleteTask() {
        // get input from user delete the task and then save to data storage

        Task task;
        output.print("Enter task id");
        String id = input.getInput();
        task = dataStorage.getTask(id);

        dataStorage.deleteTask(task);
    }
    public void updateTask() {
        try {
        Task task = getUpdatedTaskFromUser();
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



    public Task getTask(String id) {
        return dataStorage.loadTask(id);
    }
    public List<Task> getAllTasks() {
        return dataStorage.getAllTasks();
    }
    private Task getUpdatedTaskFromUser() throws WrongCategoryError, WrongStatusError, WrongPriorityError, WrongFieldError, ParseException {
        output.print("Enter task id for update");
        String id = input.getInput();
        Task task = dataStorage.getTask(id);
        output.print("Enter the field you want to update");
        output.print("1: Name, 2: Description, 3: Category, 4: Priority, 5: Status, 6: Due Date");
        String field = input.getInput();
        switch (field) {
            case "1":
                output.print("Enter new name");
                String name = input.getInput();
                task.setName(name);
                break;
            case "2":
                output.print("Enter new description");
                String description = input.getInput();
                task.setDescription(description);
                break;
            case "3":
                output.print("Enter new category");
                output.print("1: WORK, 2: PERSONAL, 3: STUDY, 4: HEALTH,");
                output.print("5: SHOPPING, 6: SOCIAL, 7: TRAVEL, 8:FINANCIAL");
                output.print("9: ENTERTAINMENT, 10: OTHER");
                String categoryString = input.getInput();
                Category category;
                switch (categoryString) {
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
                    case "10":
                        category = Category.OTHER;
                        break;
                    default:
                        throw new WrongCategoryError("Invalid category");
                }
                task.setCategory(category);
                break;
            case "4":
                output.print("Enter new priority");
                output.print("1: LOW, 2: MEDIUM, 3: HIGH");
                String priorityString = input.getInput();
                Priority priority;
                switch (priorityString) {
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
                task.setPriority(priority);
                break;
            case "5":
                output.print("Enter new status");
                output.print("1: CREATED, 2: IN_PROGRESS, 3: DONE");
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
                    default:
                        throw new WrongStatusError("Invalid status");
                }
                task.setStatus(status);
                break;
            case "6":
                output.print("Enter new due date in dd/MM/yyyy format");
                String dueDate = input.getInput();
                Date date=new SimpleDateFormat("dd/MM/yyyy").parse(dueDate);
                task.setDueDate(date);
                break;
            default:
                throw new WrongFieldError("Invalid field");

        }
        task.setUpdatedDate(new Date());


        return task;
    }
}