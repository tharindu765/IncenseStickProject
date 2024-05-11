package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;

public class DashboardFormController {
    public AnchorPane smallRootNood;
    public Label lblDashBordDate;
    public Label lblDashBordTime;


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

    public void imgSettingBtn(MouseEvent mouseEvent) {

    }

    public void btnDeleteAccount(ActionEvent actionEvent) {


    }

    public void btnLogOut(ActionEvent actionEvent) {

    }
}
