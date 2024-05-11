package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lk.ijse.model.tm.PackageTm;
import lk.ijse.repository.BatchRepo;
import lk.ijse.repository.CustomerRepo;
import lk.ijse.repository.PackageRepo;
import lk.ijse.repository.SaleRepo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class PackageFormController {
    public TableView<PackageTm> tblPackageType;
    public TableColumn<?,?> colPackageType;
    public TableColumn<?,?> colUnitPrice;
    public TableColumn<?,?> colQuantity;
    public TableColumn<?,?> colBatchID;
    public TextField txtPackageType;
    public TextField txtUnitPrice;
    public TextField txtQuantity;
    public ComboBox<String> cmbBatchID;
    public int packageID;
    public void initialize(){
        setCellValueFactory();
        loadAllOrder();
        getPackageId();
        getBatchID();
    }

    private void loadAllOrder() {
        try {
            List<PackageTm> packageList = PackageRepo.getAllPackageDetails();
            ObservableList<PackageTm> observableList = FXCollections.observableArrayList(packageList);
            tblPackageType.setItems(observableList);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load package details").show();
            e.printStackTrace();
        }
    }

    private void setCellValueFactory() {
        colPackageType.setCellValueFactory(new PropertyValueFactory<>("description"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colBatchID.setCellValueFactory(new PropertyValueFactory<>("batchID"));
    }
    public void btnUpdate(ActionEvent actionEvent) {

    }

    public void btnDelete(ActionEvent actionEvent) {

    }

    public void btnReset(ActionEvent actionEvent) {

    }

    public void brnSearch(ActionEvent actionEvent) {

    }

    public void btnAdd(ActionEvent actionEvent) {
        String description = txtPackageType.getText().trim();
        String unitPriceText = txtUnitPrice.getText().trim();
        String quantityText = txtQuantity.getText().trim();
        String batchID = cmbBatchID.getValue();


        if (description.isEmpty() || unitPriceText.isEmpty() || quantityText.isEmpty() || batchID == null) {
            new Alert(Alert.AlertType.ERROR, "All fields are required.").show();
            return;
        }

        try {
            double unitPrice = Double.parseDouble(unitPriceText);
            int quantity = Integer.parseInt(quantityText);
            boolean success = PackageRepo.addPackage(description, unitPrice, quantity, batchID,packageID);
            if (success) {
                new Alert(Alert.AlertType.CONFIRMATION, "Package added successfully.").show();
                loadAllOrder();
                resetFields();
                getPackageId();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to add package.").show();
            }
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid unit price or quantity.").show();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to add package.").show();
            e.printStackTrace();
        }
    }

    private void resetFields() {
        txtPackageType.clear();
        txtUnitPrice.clear();
        txtQuantity.clear();
        cmbBatchID.getSelectionModel().clearSelection();
    }
    public void btnAddBatch(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Batch_form.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Batch Form");
        stage.show();
    }
    private void getPackageId() {
        try {
            String currentId = PackageRepo.getCurrentId();
            String nextPackageId = generateNextPackageId(currentId);
            packageID = Integer.parseInt(nextPackageId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateNextPackageId(String currentId) {
        if(currentId != null) {
            int newId = Integer.parseInt(currentId);
            newId++;
            return String.valueOf(newId);
        }
        return "01";

    }
    private void getBatchID(){
        ObservableList<String> obList = FXCollections.observableArrayList();
        try{
            List<String> IDlist = BatchRepo.getBatchID();
            for (String i:IDlist){
                obList.add(i);
            }
            cmbBatchID.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
