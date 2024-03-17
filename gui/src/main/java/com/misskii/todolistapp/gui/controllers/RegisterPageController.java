package com.misskii.todolistapp.gui.controllers;

import com.misskii.todolistapp.dao.PersonDao;
import com.misskii.todolistapp.entities.Person;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RegisterPageController extends GeneralController {
    @FXML
    private TextField userName;
    @FXML
    private TextField userEmailField;
    @FXML
    private PasswordField userPasswordField;
    @FXML
    private PasswordField userPasswordField2;
    private PersonDao personDAO = new PersonDao();

    public void createNewUser(ActionEvent event) throws IOException {
        List<String> emailsList = new ArrayList<>();
        if (userName.getText().isEmpty() || userEmailField.getText().isEmpty() || userPasswordField.getText().isEmpty() || userPasswordField2.getText().isEmpty()) {
            displayError("All fields must be filled in");
            return;
        } else if (!Objects.equals(userPasswordField.getText(), userPasswordField2.getText())) {
            displayError("Passwords do not match");
            return;
        }else{
            for (int i = 0; i < personDAO.loginUser().size(); i++) {
                emailsList.add(personDAO.loginUser().get(i).getEmail());
                if (Objects.equals(emailsList.get(i), userEmailField.getText())) {
                    displayError("User with this email already exists");
                    return;
                }
            }
        }
        personDAO.createNewPerson(new Person(userName.getText(), userEmailField.getText(), userPasswordField.getText()));
        changeScene(event, "views/login-page.fxml");
    }

    public void switchToLoginPage(ActionEvent event) throws IOException {
        changeScene(event, "views/login-page.fxml");
    }
}
