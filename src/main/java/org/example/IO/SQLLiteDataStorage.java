package org.example.IO;

import org.example.IO.exceptions.CouldNotDeleteTaskException;
import org.example.IO.exceptions.CouldNotSaveTaskException;
import org.example.IO.exceptions.NoSuchTaskException;
import org.example.model.Category;
import org.example.model.Priority;
import org.example.model.Status;
import org.example.model.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SQLLiteDataStorage implements DataStorage {
    String databaseURL;
    Connection connection;
    String cachedID;

    public SQLLiteDataStorage(String URL) throws SQLException {
        databaseURL = URL;
        initializeDatabase();
    }

    private void initializeDatabase() throws SQLException{
        Connection connection = DriverManager.getConnection(databaseURL);
        Statement statement = connection.createStatement();

        statement.execute(
                "CREATE TABLE IF NOT EXISTS tasks (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT, " +
                    "description TEXT, " +
                    "category NUMBER, " +
                    "priority NUMBER, " +
                    "status NUMBER, " +
                    "dueDate DATE, " +
                    "createdDate DATE, " +
                    "updatedDate DATE" +
                    ")"
        );
        connection.close();
    }
    @Override
    public void saveTask(Task task) throws CouldNotSaveTaskException {
        try {
            Statement statement = connection.createStatement();
            statement.execute(
                    "INSERT INTO tasks (name, description, category, priority, status, dueDate, createdDate, updatedDate) " +
                            "VALUES (" +
                            task.getName() + ", " +
                            task.getDescription() + ", " +
                            task.getCategory().ordinal() + ", " +
                            task.getPriority().ordinal() + ", " +
                            task.getStatus().ordinal() + ", " +
                            task.getDueDate() + ", " +
                            task.getCreatedDate() + ", " +
                            task.getUpdatedDate() +
                            ")"
            );
        } catch (SQLException e) {
            throw new CouldNotSaveTaskException("Problem saving task to database"+e.getMessage());
        }
    }

    @Override
    public void deleteTask(Task id) throws CouldNotDeleteTaskException {
        try {
            Statement statement = connection.createStatement();
            statement.execute(
                    "DELETE FROM tasks WHERE id = " + id
            );
        } catch (SQLException e) {
            throw new CouldNotDeleteTaskException("Problem deleting task from database"+e.getMessage());
        }
    }

    @Override
    public List<Task> getAllTasks() throws NoSuchTaskException {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM tasks";

        try (Connection connection = DriverManager.getConnection(databaseURL);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Task task = mapResultSetToTask(resultSet);
                tasks.add(task);
            }
        } catch (SQLException e) {
            throw new NoSuchTaskException("Problem getting all tasks from database\n" + e.getMessage());
        }

        return tasks;
    }



    @Override
    public Task getTask(String id) throws NoSuchTaskException {
        Task task = new Task();
        String sql = "SELECT * FROM tasks WHERE id = " + id;

        try (Connection connection = DriverManager.getConnection(databaseURL);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                task = mapResultSetToTask(resultSet);
            }
        } catch (SQLException e) {
            throw new NoSuchTaskException("Problem getting task from database\n" + e.getMessage());
        }

        return task;
    }

    @Override
    public String getNewId() {
        // Possibly wrong since multiple clients could be accessing the database at the same time
        if(cachedID!= null) {
            return cachedID+1;
        }

        String newId = null;
        String maxIdSql = "SELECT max(id) FROM tasks";

        try (Connection connection = DriverManager.getConnection(databaseURL);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(maxIdSql)) {

            if (resultSet.next()) {
                int maxId = resultSet.getInt(1);
                newId = String.valueOf(maxId + 1);
            } else {
                // If the table is empty, start with ID 1
                newId = "1";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        // Cache the new ID in memory
        cachedID = newId;

        return newId;
    }

    private Task mapResultSetToTask(ResultSet resultSet) throws NoSuchTaskException {
        Task task = new Task();
        try {
            task.setId(resultSet.getString("id"));
            task.setName(resultSet.getString("name"));
            task.setDescription(resultSet.getString("description"));
            task.setCategory(Category.values()[resultSet.getInt("category")]);
            task.setPriority(Priority.values()[resultSet.getInt("priority")]);
            task.setStatus(Status.values()[resultSet.getInt("status")]);
            task.setDueDate(resultSet.getDate("dueDate"));
            task.setCreatedDate(resultSet.getDate("createdDate"));
            task.setUpdatedDate(resultSet.getDate("updatedDate"));
        } catch (SQLException e) {
            throw new NoSuchTaskException("Problem mapping result set to task" + e.getMessage());
        }
        return task;
    }
}