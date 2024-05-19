package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.repository.UserRepo;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

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
        setDate();
        setLTime();
    }

    private void setLTime() {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            lblDashBordTime.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Timeline.INDEFINITE);
        clock.play();
    }

    private void setDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        lblDashBordDate.setText(LocalDate.now().format(formatter));
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




    public void btnDeleteAccount(ActionEvent actionEvent) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Delete Account");
        dialog.setHeaderText("Please enter your password to confirm account deletion:");
        dialog.setContentText("Password:");
        Optional<String> result = dialog.showAndWait();

        result.ifPresent(password -> {
            try {
                if (UserRepo.checkPassword(password)) {
                    boolean isDeleteTruncate = UserRepo.truncateUserTable();
                    if (!isDeleteTruncate) {
                        System.out.println("hi");
                        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/view/Login_form.fxml")));
                        Stage stage = (Stage) this.rootNood.getScene().getWindow();
                        stage.setScene(scene);
                        stage.centerOnScreen();
                        stage.setTitle("Login Form");
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Failed to delete account!").show();
                    }
                } else {
                    new Alert(Alert.AlertType.ERROR, "Incorrect password!").show();
                }
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
    public void btnLogOut(ActionEvent actionEvent) throws IOException {
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/view/Login_form.fxml")));
        Stage stage= (Stage) this.rootNood.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Login Form");
    }
}
