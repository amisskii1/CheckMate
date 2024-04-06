package com.misskii.javatodolistapp.Controllers;

import com.misskii.javatodolistapp.DAO.LicenseDAO;
import com.misskii.javatodolistapp.Util.PersonNotExistsException;
import com.misskii.javatodolistapp.license.LicenseClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class LicensePageController extends GeneralController {
    @FXML
    private TextField licenseValue;
    @FXML
    private TextField email;

    private final LicenseClient licenseClient = new LicenseClient();
    private final LicenseDAO licenseDAO = new LicenseDAO();
    public void submitLicense(ActionEvent event) throws IOException {
        if (!isEmailFormatValid(email.getText())) {
            displayError("Email is not valid");
            return;
        }
       if (!isLicenseValueValid()) {
           displayError("License value is not valid");
           return;
       }
       try {
           licenseDAO.save(email.getText(),licenseValue.getText());
       }catch (SQLException e) {
           licenseDAO.update(email.getText(), licenseValue.getText());
       } catch (PersonNotExistsException e){
           displayError("User with this email does not exist");
           return;
       }
       changeScene(event, "login-page.fxml");
    }

    public void getTrialLicense(ActionEvent event) throws IOException {
        if (!isEmailFormatValid(email.getText())) {
            displayError("Email is not valid");
            return;
        }
        String licenseTrialResult = licenseClient.createTrialLicense(email.getText());
        if (Objects.equals(licenseTrialResult, "Trial license can not be activated")){
            displayError("Trial license can not be activated");
            return;
        }
        try {
            licenseDAO.save(email.getText(),licenseTrialResult);
        } catch (SQLException e) {
            displayError("Trial license can not be activated");
        } catch (PersonNotExistsException e){
            System.out.println("afafafa");
            displayError("User with this email does not exist");
            return;
        }
        changeScene(event, "login-page.fxml");
    }

    public boolean isLicenseValueValid(){
        return !licenseValue.getText().isEmpty();
    }

    public void cancel(ActionEvent event) throws IOException {
        changeScene(event, "login-page.fxml");
    }

}
