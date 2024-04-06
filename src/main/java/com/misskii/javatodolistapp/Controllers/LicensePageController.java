package com.misskii.javatodolistapp.Controllers;

import com.misskii.javatodolistapp.DAO.LicenseDAO;
import com.misskii.javatodolistapp.DAO.PersonDAO;
import com.misskii.javatodolistapp.license.LicenseClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.Objects;

public class LicensePageController extends GeneralController {
    @FXML
    private TextField licenseValue;
    @FXML
    private TextField email;

    private final LicenseClient licenseClient = new LicenseClient();
    private final LicenseDAO licenseDAO = new LicenseDAO();
    public void submitLicense(ActionEvent event) throws IOException {
        if (email.getText().isEmpty()) displayError("Email field should not be empty");
        if (licenseValue.getText().isEmpty()) displayError("License key field should not be empty");
        licenseDAO.save(email.getText(),licenseValue.getText());
        changeScene(event, "login-page.fxml");
    }

    public void getTrialLicense(ActionEvent event) throws IOException {
        if (email.getText().isEmpty()) displayError("Email field should not be empty");
        String licenseTrialResult = licenseClient.createTrialLicense(email.getText());
        if (Objects.equals(licenseTrialResult, "Trial license can not be activated")){
            displayError("Trial license can not be activated");
            return;
        }
        licenseDAO.save(email.getText(),licenseTrialResult);
        changeScene(event, "login-page.fxml");
    }

    public void cancel(ActionEvent event) throws IOException {
        changeScene(event, "login-page.fxml");
    }

}
