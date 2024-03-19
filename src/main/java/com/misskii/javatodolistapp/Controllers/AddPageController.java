package com.misskii.javatodolistapp.Controllers;

import com.misskii.javatodolistapp.DAO.TaskDAO;
import com.misskii.javatodolistapp.Models.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.sql.Date;

public class AddPageController extends GeneralController {
    private TaskDAO taskDAO = new TaskDAO();

    @FXML
    private TextField taskDate;

    @FXML
    private TextField taskTitle;

    @FXML
    private TextField taskDescription;

    public void createNewTask(ActionEvent event) throws IOException {
        if (!isValidDate(taskDate.getText())){
            displayError("Date field must be in format yyyy-MM-dd");
            return;
        }
        taskDAO.createNewTask(new Task(taskTitle.getText(), taskDescription.getText(), Date.valueOf(taskDate.getText()), currentUser(getUserId()) + 1, "In Progress"));
        switchToMainPage(event);
    }

    public void cancel(ActionEvent event) throws IOException {
        switchToMainPage(event);
    }
}
