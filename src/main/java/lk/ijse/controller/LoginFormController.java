package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import lk.ijse.Util.Regex;
import lk.ijse.repository.UserRepo;
import org.jfree.data.xy.TableXYDataset;

import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;

public class LoginFormController {
    public AnchorPane rootNood;
    public PasswordField txtPassword;
    public TextField txtUserName;
    public JFXButton btnLoginButton;


    public void btnlLogin(ActionEvent actionEvent) {
        String username = txtUserName.getText();
        String password = txtPassword.getText();

        try {
            if (UserRepo.authenticateUser(username, password)) {
                try {
                    Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/view/Dashboard_form.fxml")));
                    Stage stage = (Stage) rootNood.getScene().getWindow();
                    stage.setResizable(false);
                    stage.setScene(scene);
                    stage.centerOnScreen();
                    stage.setTitle("Dashboard form");
                } catch (IOException e) {
                    e.printStackTrace();
                    new Alert(Alert.AlertType.ERROR, "An error occurred while loading the dashboard").show();
                }
            } else {
                new Alert(Alert.AlertType.ERROR, "Invalid username or password").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
        public void focusUsertxt(){
        txtUserName.requestFocus();
        }

    public void hypRegister(ActionEvent actionEvent) throws IOException {
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/view/Register_form.fxml")));
        Stage stage= (Stage) this.rootNood.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Register form");
    }

    public void txtUserNameAction(ActionEvent actionEvent) {
    txtPassword.requestFocus();
    }

    public void txtPasswordAction(ActionEvent actionEvent) {
btnLoginButton.requestFocus();
    }

    public void userNameKeyRelese(KeyEvent keyEvent) {

    }

    public void PasswordKeyRelease(KeyEvent keyEvent) {

    }
}
