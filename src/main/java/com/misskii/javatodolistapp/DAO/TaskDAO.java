package com.misskii.javatodolistapp.DAO;

import com.misskii.javatodolistapp.Models.Task;
import com.misskii.javatodolistapp.Util.DBUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class TaskDAO {
    private final Connection connection = DBUtil.getConnection();

    public void createNewTask(Task task){
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO tasks (task_title, task_description, task_date, task_status, user_id) VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, task.getTaskTitle());
            preparedStatement.setString(2, task.getTaskDescription());
            preparedStatement.setDate(3, task.getDate());
            preparedStatement.setString(4, task.getStatus());
            preparedStatement.setInt(5, task.getPersonId());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()){
                int id = resultSet.getInt(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void updateTaskByID(int personId,String id, String description, String title, Date date, String status){
        try {
            int taskId = Integer.parseInt(id);
            PreparedStatement preparedStatement =
                    connection.prepareStatement("UPDATE tasks SET task_title=?, task_description=?, task_date=?, task_status=? WHERE tasks.task_id=? AND tasks.user_id=?");
            preparedStatement.setString(1, description);
            preparedStatement.setString(2, title);
            preparedStatement.setDate(3, date);
            preparedStatement.setString(4, status);
            preparedStatement.setInt(5, taskId);
            preparedStatement.setInt(6, personId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ObservableList<Task> selectAllTasksByPersonId(int id){
        ObservableList<Task> observableList = FXCollections.observableArrayList();
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT task_id, task_title, task_description, task_date, task_status from tasks JOIN users ON tasks.user_id=users.user_id WHERE tasks.user_id=?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Task task = new Task();
                task.setTaskId(resultSet.getInt("task_id"));
                task.setTaskTitle(resultSet.getString("task_title"));
                task.setTaskDescription(resultSet.getString("task_description"));
                task.setDate(resultSet.getDate("task_date"));
                task.setStatus(resultSet.getString("task_status"));
                observableList.add(task);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return observableList;
    }

    public Task getTaskByID(String id){
        Task task = new Task();
        try {
            int taskId = Integer.parseInt(id);
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM tasks WHERE task_id=?");
            preparedStatement.setInt(1, taskId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                task.setTaskId(resultSet.getInt("task_id"));
                task.setTaskTitle(resultSet.getString("task_title"));
                task.setTaskDescription(resultSet.getString("task_description"));
                task.setDate(resultSet.getDate("task_date"));
                task.setStatus(resultSet.getString("task_status"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return task;
    }
}
