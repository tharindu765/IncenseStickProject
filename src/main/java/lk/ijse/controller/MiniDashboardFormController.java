package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import lk.ijse.model.IncensePackage;
import lk.ijse.model.Sale;
import lk.ijse.repository.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class MiniDashboardFormController {
    public Label lblCustomerCount;
    public Label lblOrderCount;
    public Label lblNetWorth;
    public Label lblSupplierCount;
    public javafx.scene.chart.PieChart PieChart;
    public javafx.scene.chart.PieChart PieChart1;

    public Label lblMopnthlyProfit;

    public void initialize(){
        setCustomerCount();
        setSupplierName();
        setOrderCount();
        setMonthlyProfit();
        setNetWorth();
        populatePieChart();
        populatePieChart1();
    }

    private void setMonthlyProfit() {
        try {
            double monthlyProfit = DashbordRepo.getCurrentMonthProfit();
            lblMopnthlyProfit.setText(String.format("%.2f", monthlyProfit));
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    private void populatePieChart1() {
        try {
            List<Sale> sales = SaleRepo.getAllSalesByPaymentStatus();

            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

            for (Sale sale : sales) {
                pieChartData.add(new PieChart.Data(sale.getPType(), sale.getCount()));
            }

            PieChart1.setData(pieChartData);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    private void populatePieChart() {
        try {
            List<IncensePackage> packages = IncensePackageRepo.getAllPackages();

            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

            for (IncensePackage pack : packages) {
                pieChartData.add(new PieChart.Data(pack.getDescription(), pack.getQty()));
            }

            PieChart.setData(pieChartData);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }
    private void setSupplierName() {
        try {
            int totalSuppliers = SupplierRepo.getTotalSupplierCount();
           lblSupplierCount.setText(String.valueOf(totalSuppliers));
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    private void setNetWorth() {
        try {
            double netWorth = OrderRepo.calculateNetWorth();
            lblNetWorth.setText(String.format(String.valueOf(netWorth)));
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }
    private void setOrderCount() {
        try {
            int orderCount = OrderRepo.getOrderCount();
             lblOrderCount.setText(String.valueOf(orderCount));
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    private void setCustomerCount() {
        try {
            int customerCount = CustomerRepo.getCustomerCount();
            lblCustomerCount.setText(String.valueOf(customerCount));
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();

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
