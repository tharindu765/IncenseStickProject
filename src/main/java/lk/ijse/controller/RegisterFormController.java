package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterFormController {
    public AnchorPane rootNood;

    public void btnlbacktoLogin(ActionEvent actionEvent) throws IOException {
        Scene scene=new Scene(FXMLLoader.load(getClass().getResource("/view/Dashboard_form.fxml")));
        Stage stage = (Stage) this.rootNood.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("DashBoard form");

    }
}
