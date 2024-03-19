package com.misskii.javatodolistapp.Controllers;

import com.misskii.javatodolistapp.DAO.TaskDAO;
import com.misskii.javatodolistapp.Models.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.IOException;
import java.sql.Date;

public class MainPageController extends GeneralController {
    private int userId;
    @FXML
    private TableView<Task> table;
    @FXML
    private TableColumn<Task, Integer> tableId;
    @FXML
    private TableColumn<Task, String> tableTitle;
    @FXML
    private TableColumn<Task, String> tableDescription;
    @FXML
    private TableColumn<Task, Date> tableDueTo;
    @FXML
    private TableColumn<Task, String> tableStatus;
    TaskDAO taskDAO = new TaskDAO();

    public void fillTable(){
        for (int i = 0; i < taskDAO.selectAllTasksByPersonId(this.userId).size(); i++){
            tableId.setCellValueFactory(new PropertyValueFactory<Task, Integer>("taskId"));
            tableTitle.setCellValueFactory(new PropertyValueFactory<Task, String>("taskTitle"));
            tableDescription.setCellValueFactory(new PropertyValueFactory<Task, String>("taskDescription"));
            tableDueTo.setCellValueFactory(new PropertyValueFactory<Task, Date>("date"));
            tableStatus.setCellValueFactory(new PropertyValueFactory<Task, String>("status"));
            table.setItems(taskDAO.selectAllTasksByPersonId(this.userId));
        }
    }

    public void displayUser(int id){
        this.userId = id;
    }

    public void switchToCreate(ActionEvent event) throws IOException {
        switchFromMainPage(event, "add-page.fxml", this.userId);
    }

    public void switchToEdit(ActionEvent event) throws IOException {
        switchFromMainPage(event, "edit-page.fxml", this.userId);
    }

    public void aboutStage(ActionEvent event) throws IOException {
        openStage("about.fxml");
    }
}
