package com.misskii.javatodolistapp.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.misskii.javatodolistapp.DAO.LicenseDAO;
import com.misskii.javatodolistapp.DAO.TaskDAO;
import com.misskii.javatodolistapp.Models.Task;
import com.misskii.javatodolistapp.Updater.Updater;
import com.misskii.javatodolistapp.license.LicenseClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

import java.io.IOException;
import java.sql.Date;
import java.util.Objects;

public class EditPageController extends GeneralController {
    private int userId;
    private boolean licenseStatus;
    @FXML
    public ToggleGroup priority;
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
    @FXML
    private RadioButton priority1;
    @FXML
    private RadioButton priority2;
    @FXML
    private RadioButton priority3;
    @FXML
    private RadioButton priorityDefault;

    private final TaskDAO taskDAO = new TaskDAO();

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
                    taskTitle.getText(), Date.valueOf(taskExp.getText()), status, setPriority());
        }
        if (taskTitle.getText().isEmpty()){
            taskDAO.updateTaskByID(currentUser(getUserId())+1 ,taskID.getText(), task.getTaskDescription(),
                    task.getTaskTitle(), Date.valueOf(taskExp.getText()), status, setPriority());
        }
        if (taskExp.getText().isEmpty()){
            taskDAO.updateTaskByID(currentUser(getUserId())+1 ,taskID.getText(), task.getTaskDescription(),
                    taskTitle.getText(), task.getDate(), status, setPriority());
        }
        if (taskDesk.getText().isEmpty() && taskTitle.getText().isEmpty()){
            taskDAO.updateTaskByID(currentUser(getUserId())+1 ,taskID.getText(), task.getTaskDescription(),
                    task.getTaskTitle(), Date.valueOf(taskExp.getText()), status, setPriority());
        }
        if (taskDesk.getText().isEmpty() && taskExp.getText().isEmpty()){
            taskDAO.updateTaskByID(currentUser(getUserId())+1 ,taskID.getText(), task.getTaskDescription(),
                    taskTitle.getText(), task.getDate(), status, setPriority());
        }
        if (taskTitle.getText().isEmpty() && taskExp.getText().isEmpty()){
            taskDAO.updateTaskByID(currentUser(getUserId())+1 ,taskID.getText(), taskDesk.getText(),
                    task.getTaskTitle(), task.getDate(), status, setPriority());
        }
        if (taskExp.getText().isEmpty() && taskTitle.getText().isEmpty()
                && taskDesk.getText().isEmpty()) {
            taskDAO.updateTaskByID(currentUser(getUserId())+1 ,taskID.getText(), task.getTaskDescription(),
                    task.getTaskTitle(), task.getDate(), status, setPriority());
        }
        if (!taskDesk.getText().isEmpty() && !taskTitle.getText().isEmpty() && !taskExp.getText().isEmpty()){
            taskDAO.updateTaskByID(currentUser(getUserId())+1 ,taskID.getText(), taskDesk.getText(),
                    taskTitle.getText(), Date.valueOf(taskExp.getText()), status, setPriority());
        }
        switchToMainPage(event, licenseStatus);
    }

    public String setPriority(){
        if (priority1.isSelected()){
            return "priority1";
        } else if (priority2.isSelected()) {
            return "priority2";
        } else if (priority3.isSelected()) {
            return "priority3";
        }
        return "default";
    }

    public void cancel(ActionEvent event) throws IOException {
        System.out.println(licenseStatus);
        switchToMainPage(event, licenseStatus);
    }

    public void setLicenseStatus(boolean licenseStatus) {
        this.licenseStatus = licenseStatus;
    }

    public void checkLicense(boolean status) {
        priorityDefault.setSelected(true);
        if (!status){
            priority1.setDisable(true);
            priority2.setDisable(true);
            priority3.setDisable(true);
        }
    }
}
