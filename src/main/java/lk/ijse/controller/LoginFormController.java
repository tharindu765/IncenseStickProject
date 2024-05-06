package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import java.awt.*;
import java.io.IOException;

public class LoginFormController {
    public AnchorPane rootNood;
    public PasswordField txtPassword;
    public TextField txtUserName;


    public void btnlRegister(ActionEvent actionEvent) throws IOException {
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/view/Register_form.fxml")));
       Stage stage= (Stage) this.rootNood.getScene().getWindow();
       stage.setScene(scene);
       stage.centerOnScreen();
       stage.setTitle("Register form");
    }

    public void btnlLogin(ActionEvent actionEvent) throws IOException {
        String username = txtUserName.getText();
        String pw = txtPassword.getText();
        if (username.equals("kirito")){
            if (pw.equals("123")){
                Scene scene=new Scene(FXMLLoader.load(getClass().getResource("/view/Dashboard_form.fxml")));
                Stage stage = (Stage) this.rootNood.getScene().getWindow();
                stage.setResizable(false);
                stage.setScene(scene);
                stage.centerOnScreen();
                stage.setTitle("DashBoard form");
            }else
            {
            new Alert(Alert.AlertType.INFORMATION,"Wrong password").show();
            }
        }else{
            new Alert(Alert.AlertType.ERROR,"Wrong username").show();
        }
        }
        public void focusUsertxt(){
        txtUserName.requestFocus();
        }
}
