package com.misskii.javatodolistapp.Controllers;

import com.misskii.javatodolistapp.DAO.TaskDAO;
import com.misskii.javatodolistapp.Models.Task;
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
        if (!taskExp.getText().isEmpty() && !isValidDate(taskExp.getText())){
            displayError("Date field must be in format yyyy-MM-dd");
            return;
        }
        task = taskDAO.getTaskByID(taskID.getText());
        if (task == null) {
            displayError("Task with ID " + taskID.getText() + " not found");
            return;
        }
        String status = markDone.isSelected() ? "Done" : "In Progress";
        String description = taskDesk.getText().isEmpty() ? task.getTaskDescription() : taskDesk.getText();
        String title = taskTitle.getText().isEmpty() ? task.getTaskTitle() : taskTitle.getText();
        Date expirationDate = taskExp.getText().isEmpty() ? task.getDate() : Date.valueOf(taskExp.getText());
        taskDAO.updateTaskByID(currentUser(getUserId()) + 1, taskID.getText(), description, title, expirationDate, status, setPriority());
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
