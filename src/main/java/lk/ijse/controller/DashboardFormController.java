package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.repository.UserRepo;

import java.io.IOException;
import java.sql.SQLException;

public class DashboardFormController {
    public AnchorPane smallRootNood;
    public Label lblDashBordDate;
    public Label lblDashBordTime;
    public AnchorPane rootNood;


    public void initialize() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/MiniDashboard_form.fxml"));
        Parent load = fxmlLoader.load();
        smallRootNood.getChildren().clear();
        smallRootNood.getChildren().add(load);
    }
    public void btnlCustomerForm(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Customer_form.fxml"));
        Parent load = fxmlLoader.load();
        smallRootNood.getChildren().clear();
        smallRootNood.getChildren().add(load);
    }

    public void btnOrder(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Order_form.fxml"));
        Parent load = fxmlLoader.load();
        smallRootNood.getChildren().clear();
        smallRootNood.getChildren().add(load);

    }

    public void btnHome(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/MiniDashboard_form.fxml"));
        Parent load = fxmlLoader.load();
        smallRootNood.getChildren().clear();
        smallRootNood.getChildren().add(load);
    }

    public void btnSales(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Sale_form.fxml"));
        Parent load = fxmlLoader.load();
        smallRootNood.getChildren().clear();
        smallRootNood.getChildren().add(load);

    }
    public void btnSupplier(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Supplier_form.fxml"));
        Parent load = fxmlLoader.load();
        smallRootNood.getChildren().clear();
        smallRootNood.getChildren().add(load);}
    public void btnMaterial(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Material_form.fxml"));
        Parent load = fxmlLoader.load();
        smallRootNood.getChildren().clear();
        smallRootNood.getChildren().add(load);
    }



    public void btnDeleteAccount(ActionEvent actionEvent)  {
        try {
           boolean isDeleteTruncate = UserRepo.truncateUserTable();
            if (isDeleteTruncate){
            //   new Alert(Alert.AlertType.CONFIRMATION,"succefully delete account!");
                Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/view/Login_form.fxml")));
                Stage stage= (Stage) this.rootNood.getScene().getWindow();
                stage.setScene(scene);
                stage.centerOnScreen();
                stage.setTitle("Login Form");
            }else {
                new Alert(Alert.AlertType.ERROR,"Faile delete account!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void btnLogOut(ActionEvent actionEvent) throws IOException {
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/view/Login_form.fxml")));
        Stage stage= (Stage) this.rootNood.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Login Form");
    }
}
