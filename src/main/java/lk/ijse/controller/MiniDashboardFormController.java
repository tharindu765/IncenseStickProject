package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import lk.ijse.repository.CustomerRepo;
import lk.ijse.repository.OrderRepo;

import java.io.IOException;
import java.sql.SQLException;

public class MiniDashboardFormController {
    public Label lblCustomerCount;
    public Label lblOrderCount;
    public Label lblNetWorth;

    public void initialize(){
        setCustomerCount();
        setOrderCount();
        setNetWorth();

    }

    private void setNetWorth() {
        try {
            double netWorth = OrderRepo.calculateNetWorth();
            lblNetWorth.setText(String.format(String.valueOf(netWorth)));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void setOrderCount() {
        try {
            int orderCount = OrderRepo.getOrderCount();
             lblOrderCount.setText(String.valueOf(orderCount));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setCustomerCount() {
        try {
            int customerCount = CustomerRepo.getCustomerCount();
            lblCustomerCount.setText(String.valueOf(customerCount));
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }
    @FXML
   public void btnPackage(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Package_form.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Package Form");
        stage.show();
    }
}
