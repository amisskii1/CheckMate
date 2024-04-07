package com.misskii.javatodolistapp.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.misskii.javatodolistapp.DAO.LicenseDAO;
import com.misskii.javatodolistapp.DAO.TaskDAO;
import com.misskii.javatodolistapp.Models.Task;
import com.misskii.javatodolistapp.license.LicenseClient;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.IOException;
import java.sql.Date;
import java.util.Objects;

public class MainPageController extends GeneralController {
    private int userId;
    private boolean licenseStatus;
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
    private final TaskDAO taskDAO = new TaskDAO();
    private final LicenseDAO licenseDAO = new LicenseDAO();
    private final LicenseClient licenseClient = new LicenseClient();

    public void fillTable() throws JsonProcessingException {
        ObservableList<Task> tasks = taskDAO.selectAllTasksByPersonId(this.userId);
        tableId.setCellValueFactory(new PropertyValueFactory<Task, Integer>("taskId"));
        tableTitle.setCellValueFactory(new PropertyValueFactory<Task, String>("taskTitle"));
        tableDescription.setCellValueFactory(new PropertyValueFactory<Task, String>("taskDescription"));
        tableDueTo.setCellValueFactory(new PropertyValueFactory<Task, Date>("date"));
        tableStatus.setCellValueFactory(new PropertyValueFactory<Task, String>("status"));
        setCellFactoryBasedOnLicenseStatus(this.licenseStatus);
        table.setItems(tasks);
    }

    public void setCellFactoryBasedOnLicenseStatus(boolean status){
        if (status){
            setCellFactoryForColumn(tableId);
            setCellFactoryForColumn(tableTitle);
            setCellFactoryForColumn(tableDescription);
            setCellFactoryForColumn(tableDueTo);
            setCellFactoryForColumn(tableStatus);
        }
    }

    private <T> void setCellFactoryForColumn(TableColumn<Task, T> column) {
        column.setCellFactory(tc -> new TableCell<Task, T>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || empty) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item.toString());
                    String priority = getTableView().getItems().get(getIndex()).getPriorityStatus();
                    setStyle(styleByPriority(priority));
                }
            }
        });
    }

    public void displayUser(int id){
        this.userId = id;
    }

    public void switchToCreate(ActionEvent event) throws IOException {
        switchToCreatePage(event, this.userId, licenseStatus);
    }

    public void switchToEdit(ActionEvent event) throws IOException {
        switchToEditPage(event, this.userId, licenseStatus);
    }

    public void aboutStage(ActionEvent event) throws IOException {
        openStage("about.fxml");
    }

    public String styleByPriority(String priority){
        if (Objects.equals(priority, "priority1")){
            return "-fx-text-fill: green;";
        } else if (Objects.equals(priority, "priority2")) {
            return "-fx-text-fill: blue;";
        } else if (Objects.equals(priority, "priority3")) {
            return "-fx-text-fill: orange;";
        }
        return "";
    }
    public void setLicenseStatus(boolean licenseStatus) {
        this.licenseStatus = licenseStatus;
    }
}
