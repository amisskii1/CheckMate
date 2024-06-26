package com.misskii.javatodolistapp.Controllers;

import com.misskii.javatodolistapp.DAO.LicenseDAO;
import com.misskii.javatodolistapp.DAO.PersonDAO;
import com.misskii.javatodolistapp.Updater.Updater;
import com.misskii.javatodolistapp.license.LicenseClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.springframework.web.client.ResourceAccessException;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class LoginPageController extends GeneralController {
    @FXML
    private TextField userEmail;
    @FXML
    private PasswordField userPassword;
    @FXML
    private TextArea version;

    private final PersonDAO personDAO = new PersonDAO();
    private final LicenseClient licenseClient = new LicenseClient();
    private final LicenseDAO licenseDAO = new LicenseDAO();

    public void switchToApp(ActionEvent event) throws IOException {
        if (!isEmailFormatValid(userEmail.getText())) {
            displayError("Email is not valid");
            return;
        }
        for (int i = 0; i < personDAO.loginUser().size(); i++) {
            if (Objects.equals(personDAO.loginUser().get(i).getEmail(), userEmail.getText())
                    && Objects.equals(personDAO.loginUser().get(i).getPassword(), userPassword.getText())) {
                try {
                    List<String> licenseData = licenseClient.validateLicenseKey(userEmail.getText(), licenseDAO.getLicenseValueByUserID(i+1));
                    licenseDAO.updateLicenseStatus(licenseData.get(0), LocalDateTime.parse(licenseData.get(1)),i+1);
                }catch (ResourceAccessException e){
                    if (!LocalDateTime.now().isBefore(licenseDAO.getExpireDate(i+1))){
                        licenseDAO.updateLicenseStatus("invalid", i+1);
                    }
                }
                String licenseStatus = licenseDAO.getLicenseStatus(i+1);
                if (Objects.equals(licenseStatus, "valid")){
                    displayLicenseConfirmation("Your license is active and valid");
                }else{
                    displayLicenseConfirmation("Your license is not active or invalid");
                }
                switchToMainPage(event, i, licenseStatus);
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
                    "\nhttps://github.com/amisskii1/todolistApp/packages/1962002");
        }else version.setText("You're all up to date with the latest version!");
    }

    public void switchToLicense(ActionEvent event) throws IOException {
        changeScene(event, "license-page.fxml");
    }
}
