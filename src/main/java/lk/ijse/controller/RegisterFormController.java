package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.repository.UserRepo;

import java.io.IOException;
import java.sql.SQLException;

public class RegisterFormController {
    public AnchorPane rootNood;
    public TextField txtUserName;
    public TextField txtPassword;
    public TextField txtConfirmPassword;
    public TextField txtID;

    public void btnlbacktoLogin(ActionEvent actionEvent) throws IOException {
        Scene scene=new Scene(FXMLLoader.load(getClass().getResource("/view/Login_form.fxml")));
        Stage stage = (Stage) this.rootNood.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("DashBoard form");

    }


    public void btnRegister(ActionEvent actionEvent) {
        String ID = txtID.getText().trim();
        String username = txtUserName.getText().trim();
        String password = txtPassword.getText().trim();
        String confirmPassword = txtConfirmPassword.getText().trim();

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "All fields are required.").show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            new Alert(Alert.AlertType.ERROR, "Passwords do not match.").show();
            return;
        }
        boolean success = false;
        try {
            success = UserRepo.registerUser(username, password,ID);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (success) {
            reset();
            new Alert(Alert.AlertType.CONFIRMATION,"User registered successfully.").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to register user.").show();
        }
    }
    public void reset(){
        txtID.clear();
        txtPassword.clear();
        txtUserName.clear();
        txtConfirmPassword.clear();
    }

    public void txtUserNameAction(KeyEvent keyEvent) {

    }

    public void txtPasswordAction(KeyEvent keyEvent) {

    }
}
