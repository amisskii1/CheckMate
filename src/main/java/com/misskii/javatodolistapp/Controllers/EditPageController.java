package com.misskii.javatodolistapp.Controllers;

import com.misskii.javatodolistapp.DAO.TaskDAO;
import com.misskii.javatodolistapp.Models.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.Date;

public class EditPageController extends GeneralController {
    @FXML
    private TextField taskID;
    @FXML
    private TextField taskTitle;
    @FXML
    private TextField taskDesk;
    @FXML
    private TextField taskExp;
    @FXML
    private CheckBox markDone;
    private TaskDAO taskDAO = new TaskDAO();

    private Task task;


    public void confChangeTask(ActionEvent event) throws IOException {
        if(taskID.getText().isEmpty()){
            displayError("Id field should be filled");
            return;
        }
        if (!isValidDate(taskExp.getText())){
            displayError("Date field must be in format yyyy-MM-dd");
            return;
        }
        task = taskDAO.getTaskByID(taskID.getText());
        String status = "In Progress";
        if (markDone.isSelected()){
            status = "Done";
        }
        if (taskDesk.getText().isEmpty()){
            taskDAO.updateTaskByID(currentUser(getUserId())+1 ,taskID.getText(), task.getTaskDescription(),
                    taskTitle.getText(), Date.valueOf(taskExp.getText()), status);
        }
        if (taskTitle.getText().isEmpty()){
            taskDAO.updateTaskByID(currentUser(getUserId())+1 ,taskID.getText(), task.getTaskDescription(),
                    task.getTaskTitle(), Date.valueOf(taskExp.getText()), status);
        }
        if (taskExp.getText().isEmpty()){
            taskDAO.updateTaskByID(currentUser(getUserId())+1 ,taskID.getText(), task.getTaskDescription(),
                    taskTitle.getText(), task.getDate(), status);
        }
        if (taskDesk.getText().isEmpty() && taskTitle.getText().isEmpty()){
            taskDAO.updateTaskByID(currentUser(getUserId())+1 ,taskID.getText(), task.getTaskDescription(),
                    task.getTaskTitle(), Date.valueOf(taskExp.getText()), status);
        }
        if (taskDesk.getText().isEmpty() && taskExp.getText().isEmpty()){
            taskDAO.updateTaskByID(currentUser(getUserId())+1 ,taskID.getText(), task.getTaskDescription(),
                    taskTitle.getText(), task.getDate(), status);
        }
        if (taskTitle.getText().isEmpty() && taskExp.getText().isEmpty()){
            taskDAO.updateTaskByID(currentUser(getUserId())+1 ,taskID.getText(), taskDesk.getText(),
                    task.getTaskTitle(), task.getDate(), status);
        }
        if (taskExp.getText().isEmpty() && taskTitle.getText().isEmpty()
                && taskDesk.getText().isEmpty()) {
            taskDAO.updateTaskByID(currentUser(getUserId())+1 ,taskID.getText(), task.getTaskDescription(),
                    task.getTaskTitle(), task.getDate(), status);
        }
        if (!taskDesk.getText().isEmpty() && !taskTitle.getText().isEmpty() && !taskExp.getText().isEmpty()){
            taskDAO.updateTaskByID(currentUser(getUserId())+1 ,taskID.getText(), taskDesk.getText(),
                    taskTitle.getText(), Date.valueOf(taskExp.getText()), status);
        }
        switchToMainPage(event);
    }

    public void cancel(ActionEvent event) throws IOException {
        switchToMainPage(event);
    }
}
