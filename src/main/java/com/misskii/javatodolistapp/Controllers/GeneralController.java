package com.misskii.javatodolistapp.Controllers;

import com.misskii.javatodolistapp.Controllers.MainPageController;
import com.misskii.javatodolistapp.DAO.PersonDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GeneralController {
    private int userId;
    private Parent root;
    private Scene scene;
    private Stage stage;
    private PersonDAO personDAO = new PersonDAO();
    public int currentUser(int userId) {
        int currentUser = -1;
        for (int i = 0; i < personDAO.loginUser().size(); i++){
            if(personDAO.loginUser().get(i).getId() == userId){
                currentUser = i;
            }
        }
        return currentUser;
    }
    public void switchToMainPage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/misskii/javatodolistapp/main-page.fxml"));
        root = loader.load();
        MainPageController mainPageController = loader.getController();
        mainPageController.displayUser(personDAO.loginUser().get(currentUser(getUserId())).getId());
        mainPageController.fillTable();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToMainPage(ActionEvent event, int id) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/misskii/javatodolistapp/main-page.fxml"));
        root = loader.load();
        MainPageController mainPageController = loader.getController();
        mainPageController.displayUser(personDAO.loginUser().get(id).getId());
        mainPageController.fillTable();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchFromMainPage(ActionEvent event, String fxml, int id) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/misskii/javatodolistapp/"+fxml));
        root = loader.load();
        GeneralController generalController = loader.getController();
        generalController.setUserId(id);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    public void changeScene(ActionEvent event,String fxml) throws IOException{
        root = FXMLLoader.load(getClass().getResource("/com/misskii/javatodolistapp/"+fxml));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void displayError(String errorMessage){
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText("Input not valid");
        errorAlert.setContentText(errorMessage);
        errorAlert.showAndWait();
    }

    public void openStage(String fxml) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/misskii/javatodolistapp/"+fxml));
        Stage aboutStage = new Stage();
        aboutStage.setTitle("About");
        aboutStage.setScene(new Scene(root, 230, 237));
        aboutStage.show();
    }

    public boolean isValidDate(String input){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        try {
            Date date = sdf.parse(input);
            return sdf.format(date).equals(input);
        } catch (ParseException e) {
            return false;
        }
    }

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
}
