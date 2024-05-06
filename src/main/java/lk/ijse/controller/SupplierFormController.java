package lk.ijse.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.model.Material;
import lk.ijse.model.Supplier;
import lk.ijse.model.tm.MaterialTm;
import lk.ijse.model.tm.SupplierTm;
import lk.ijse.repository.MaterialRepo;
import lk.ijse.repository.SupplierRepo;

import java.sql.SQLException;
import java.util.List;

public class SupplierFormController {

    public TableView<SupplierTm> tblSupplier;
    public TableColumn<?,?> colName;
    public TableColumn<?,?> colTel;
    public TextField txtSupplierName;
    public TextField txtTelNumber;
    public TableColumn<?,?> colNIC;
    public TextField txtSupplierID;

    public void initialize(){
        setCellValueFactor();
        loadAllMaterial();
        tblSupplier.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                SupplierTm selectedSupplier = newSelection;
                txtSupplierID.setText(String.valueOf(selectedSupplier.getSupplierID()));
                txtSupplierName.setText(selectedSupplier.getName());
                txtTelNumber.setText(selectedSupplier.getTel());
            }
        });
    }

    private void loadAllMaterial() {
        ObservableList<SupplierTm> obList = FXCollections.observableArrayList();
        try{
            List<Supplier> supplierList = SupplierRepo.getAll();
            for(Supplier s:supplierList){
                SupplierTm tm = new SupplierTm(
                        s.getSupplierID(),
                        s.getName(),
                        s.getTel()
                );
                        obList.add(tm);
            }
            tblSupplier.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void setCellValueFactor() {
        colNIC.setCellValueFactory(new PropertyValueFactory<>("SupplierID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colTel.setCellValueFactory(new PropertyValueFactory<>("tel"));
    }


    public void btnAdd(ActionEvent actionEvent) {
        String name = txtSupplierName.getText();
        String tel = txtTelNumber.getText();
        int NIC = Integer.parseInt(txtSupplierID.getText());
        try {
            SupplierRepo.addSupplier(name, tel,NIC);
            loadAllMaterial();
            clearFields();
            new Alert(Alert.AlertType.CONFIRMATION,"Supplier added successfully.").show();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to add supplier.").show();
            e.printStackTrace();
        }
    }
    private void clearFields() {
        txtSupplierID.clear();
        txtSupplierName.clear();
        txtTelNumber.clear();
    }
    public void btnSearch(ActionEvent actionEvent) {
        String keyword = txtSupplierID.getText();
        try {
            Supplier searchedSupplier = SupplierRepo.searchSupplierById(keyword);
            if (searchedSupplier != null) {
                ObservableList<SupplierTm> obList = FXCollections.observableArrayList();
                SupplierTm tm = new SupplierTm(
                        searchedSupplier.getSupplierID(),
                        searchedSupplier.getName(),
                        searchedSupplier.getTel()
                );
                obList.add(tm);
                tblSupplier.setItems(obList);
            } else {
                new Alert(Alert.AlertType.CONFIRMATION,"Supplier Can't Find.").show();
                tblSupplier.setItems(null);
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public void btnDelete(ActionEvent actionEvent) {
        SupplierTm selectedSupplier = tblSupplier.getSelectionModel().getSelectedItem();
        if (selectedSupplier == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a supplier to delete.").show();
            return;
        }

        int supplierID = selectedSupplier.getSupplierID();
        try {
            SupplierRepo.deleteSupplier(supplierID);
            loadAllMaterial();
            clearFields();
            new Alert(Alert.AlertType.INFORMATION, "Supplier deleted successfully.").show();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to delete supplier.").show();
            e.printStackTrace();
        }
    }

    public void btnReset(ActionEvent actionEvent) {
        txtSupplierID.clear();
        txtSupplierName.clear();
        txtTelNumber.clear();
    }

    public void btnPackageDetail(ActionEvent actionEvent) {

    }
    public void btnUpdate(ActionEvent actionEvent) {

        SupplierTm selectedSupplier = tblSupplier.getSelectionModel().getSelectedItem();
        if (selectedSupplier == null) {
            new Alert(Alert.AlertType.ERROR,"Please select a supplier to update.");
            return;
        }

        String name = txtSupplierName.getText();
        String tel = txtTelNumber.getText();
        int NIC = Integer.parseInt(txtSupplierID.getText());

        try {
            SupplierRepo.updateSupplier(selectedSupplier.getSupplierID(), name, tel, NIC);
            loadAllMaterial();
            clearFields();
            new Alert(Alert.AlertType.INFORMATION,"Supplier details updated successfully.");

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,"Failed to update supplier details.");

            e.printStackTrace();
        }
    }

}
