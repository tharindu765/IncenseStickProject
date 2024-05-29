package lk.ijse.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.Util.Regex;
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
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
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
        String NIC = txtSupplierID.getText();

        if (txtSupplierID.getText().isEmpty() || txtSupplierName.getText().isEmpty() || txtTelNumber.getText().isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Please fill the data to Add").show();
            return;
        }
        if (isValied()) {
            try {
                SupplierRepo.addSupplier(name, tel, NIC);
                loadAllMaterial();
                clearFields();
                new Alert(Alert.AlertType.CONFIRMATION, "Supplier added successfully.").show();

            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Failed to add supplier.").show();
            }
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
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    public void btnDelete(ActionEvent actionEvent) {
        SupplierTm selectedSupplier = tblSupplier.getSelectionModel().getSelectedItem();
        if (selectedSupplier == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a supplier to delete.").show();
            return;
        }
        String supplierID = selectedSupplier.getSupplierID();
        try {
            SupplierRepo.deleteSupplier(supplierID);
            loadAllMaterial();
            clearFields();
            new Alert(Alert.AlertType.INFORMATION, "Supplier deleted successfully.").show();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to delete supplier.").show();
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

        if (txtSupplierID.getText().isEmpty() || txtSupplierName.getText().isEmpty() || txtTelNumber.getText().isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Please fill the data to Update").show();
            return;
        }
        String name = txtSupplierName.getText();
        String tel = txtTelNumber.getText();
        String NIC = txtSupplierID.getText();

        try {
            SupplierRepo.updateSupplier(selectedSupplier.getSupplierID(), name, tel, NIC);
            loadAllMaterial();
            clearFields();
            new Alert(Alert.AlertType.INFORMATION,"Supplier details updated successfully.");

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,"Failed to update supplier details.").show();
        }
    }

    public void txtNameAction(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.Util.TextField.NAME,txtSupplierName);
    }

    public void txtTelAction(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.Util.TextField.TELNUMBER,txtTelNumber);
    }

    public void txtIdAction(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.Util.TextField.NIC,txtSupplierID);
    }
    public boolean isValied(){
        if (!Regex.setTextColor(lk.ijse.Util.TextField.TELNUMBER,txtTelNumber)) return false;
        if (!Regex.setTextColor(lk.ijse.Util.TextField.NAME,txtSupplierName)) return false;
        if (!Regex.setTextColor(lk.ijse.Util.TextField.NIC,txtSupplierID)) return false;
        return true;
    }

}
