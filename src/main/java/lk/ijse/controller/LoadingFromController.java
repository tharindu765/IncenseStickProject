package lk.ijse.controller;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.event.*;
import lk.ijse.db.DbConnection;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.SQLException;

public class LoadingFromController{
    @FXML
    private JFXButton StartBtnl;

    @FXML
    private AnchorPane rootNood;

    public void btnlLoadStartApp(javafx.event.ActionEvent actionEvent) throws IOException {
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/view/Login_form.fxml")));
        Stage stage= (Stage) this.rootNood.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Login Form");
        try {
            DbConnection.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
