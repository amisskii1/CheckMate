package com.misskii.javatodolistapp.Controllers;

import com.misskii.javatodolistapp.DAO.PersonDAO;
import com.misskii.javatodolistapp.Updater.Updater;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.util.Objects;

public class LoginPageController extends GeneralController {
    @FXML
    private TextField userEmail;
    @FXML
    private PasswordField userPassword;
    @FXML
    private TextArea version;

    PersonDAO personDAO = new PersonDAO();

    public void switchToApp(ActionEvent event) throws IOException {
        for (int i = 0; i < personDAO.loginUser().size(); i++) {
            if (Objects.equals(personDAO.loginUser().get(i).getEmail(), userEmail.getText())
                    && Objects.equals(personDAO.loginUser().get(i).getPassword(), userPassword.getText())) {
                switchToMainPage(event, i);
                return;
            }
        }
        displayError("Incorrect email or password");
    }

    public void switchToRegistration(ActionEvent event) throws IOException {
        changeScene(event, "register-page.fxml");
    }

    public void initialize(){
        Updater updater = new Updater();
        if ( !updater.compareVersions() ){
            version.setStyle("-fx-text-fill: red;");
            version.setText("It appears you might not be using the most up-to-date version." +
                    " \nYou can download the latest version from the following link:" +
                    "\nhttps://github.com/AntonMisskii/todolistApp/packages/1962002");
        }else version.setText("You're all up to date with the latest version!");
    }

    public void switchToLicense(ActionEvent event) throws IOException {
        changeScene(event, "license-page.fxml");
    }
}
