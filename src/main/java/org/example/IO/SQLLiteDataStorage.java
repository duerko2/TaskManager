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
                    "id INTEGER PRIMARY KEY, " +
                    "nameString TEXT, " +
                    "description TEXT, " +
                    "category INTEGER, " +
                    "priority INTEGER, " +
                    "status INTEGER, " +
                    "dueDate DATE, " +
                    "createdDate DATE, " +
                    "updatedDate DATE" +
                    ")"
        );
        connection.close();
    }
    @Override
    public void saveTask(Task task) throws CouldNotSaveTaskException {
        try (Connection connection = DriverManager.getConnection(databaseURL)){
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO tasks (" +
                            "id,"+
                            "nameString, " +
                            "description, " +
                            "category, " +
                            "priority, " +
                            "status, " +
                            "dueDate, " +
                            "createdDate, " +
                            "updatedDate) " +
                    "VALUES (?,?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setInt(1,Integer.parseInt(task.getId()));
            ps.setString(2, task.getName());
            ps.setString(3, task.getDescription());
            ps.setInt(4, task.getCategory().ordinal());
            ps.setInt(5, task.getPriority().ordinal());
            ps.setInt(6, task.getStatus().ordinal());
            ps.setDate(7, new Date(task.getDueDate().getTime()));
            ps.setDate(8, new Date(task.getCreatedDate().getTime()));
            ps.setDate(9, new Date(task.getUpdatedDate().getTime()));
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new CouldNotSaveTaskException("Problem saving task to database"+e.getMessage());
        }
    }

    @Override
    public void deleteTask(Task id) throws CouldNotDeleteTaskException {
        try (Connection connection = DriverManager.getConnection(databaseURL)){
            Statement statement = connection.createStatement();
            statement.execute(
                    "DELETE FROM tasks WHERE id = " + id.getId()
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
                Task task = new Task();
                mapResultSetToTask(resultSet,task);
                tasks.add(task);
            }
            return tasks;
        } catch (SQLException e) {
            throw new NoSuchTaskException("Problem getting all tasks from database\n" + e.getMessage());
        }
    }



    @Override
    public Task getTask(String id) throws NoSuchTaskException {
        Task task = new Task();


        try (Connection connection = DriverManager.getConnection(databaseURL)){
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM tasks WHERE id = ?");
            ps.setString(1, id);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                mapResultSetToTask(resultSet,task);
                return task;
            } else {
                throw new NoSuchTaskException("Problem getting task from database");
            }
        } catch (SQLException e) {
            throw new NoSuchTaskException("Problem getting task from database\n" + e.getMessage());
        }

    }

    @Override
    public String getNewId() {
        // Possibly wrong since multiple clients could be accessing the database at the same time
        if(cachedID!= null) {
            int newId = Integer.parseInt(cachedID) + 1;
            cachedID = String.valueOf(newId);
            return cachedID;
        }

        String newId = null;

        try (Connection connection = DriverManager.getConnection(databaseURL)){

            PreparedStatement ps = connection.prepareStatement("SELECT max(id) FROM tasks");
            ResultSet resultSet = ps.executeQuery();


            if (resultSet.next()) {
                int maxId = resultSet.getInt(1)+1;
                newId = String.valueOf(maxId);
            } else {
                // If the table is empty, start with ID 1
                newId = "1";
            }

            // Cache the new ID in memory
            cachedID = newId;
            return newId;
        } catch (SQLException e) {
            e.printStackTrace();
            return "0";
        }
    }

    private void mapResultSetToTask(ResultSet resultSet, Task task) throws NoSuchTaskException {
        try {
            task.setId(resultSet.getInt("id") + "");
            task.setName(resultSet.getString("nameString"));
            task.setDescription(resultSet.getString("description"));
            task.setCategory(Category.values()[resultSet.getInt("category")]);
            task.setPriority(Priority.values()[resultSet.getInt("priority")]);
            task.setStatus(Status.values()[resultSet.getInt("status")]);
            task.setDueDate(new java.util.Date(resultSet.getDate("dueDate").getTime()));
            task.setCreatedDate(new java.util.Date(resultSet.getDate("createdDate").getTime()));
            task.setUpdatedDate(new java.util.Date(resultSet.getDate("updatedDate").getTime()));


        } catch (SQLException e) {
            throw new NoSuchTaskException("Problem mapping result set to task" + e.getMessage());
        }
    }
}
