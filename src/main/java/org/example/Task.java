package org.example;


import java.util.Date;

public class Task {
    private Category category;
    private String description;
    private String id;
    private String name;
    private Status status;
    private Priority priority;
    private Date dueDate;
    private Date createdDate;
    private Date updatedDate;


    public Task(Category category, String description, String id, String name, Status status, Priority priority, Date dueDate, Date createdDate, Date updatedDate) {
        this.category = category;
        this.description = description;
        this.id = id;
        this.name = name;
        this.status = status;
        this.priority = priority;
        this.dueDate = dueDate;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }
}
