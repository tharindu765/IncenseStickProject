package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.model.Customer;
import lk.ijse.model.Material;
import lk.ijse.model.tm.CustomerTm;
import lk.ijse.model.tm.MaterialTm;
import lk.ijse.repository.*;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class MaterialFormController {
    public TableView<MaterialTm> tblSupplier;
    public TableColumn<?,?> colMaterialName;
    public TableColumn<?,?> colQty;
    public TableColumn<?,?> colSupplierName;
    public TableColumn<?,?> colDate;
    public TableColumn<?,?> colTotalPrice;
    public TextField txtMaterialName;
    public ComboBox<String> cmbSupplierName;
    public Label lblMaterialId;
    public TextField txtUnitPrice;
    public TextField txtQty;

    public void initialize(){
        setCellValueFactor();
        loadAllMaterial();
        getCurrentMaterialId();
        getSupplierName();
    }

    private void loadAllMaterial() {
        ObservableList<MaterialTm> obList = FXCollections.observableArrayList();
        try{
            List<Material> materialList = MaterialRepo.getAll();
            for(Material material:materialList){
                MaterialTm tm = new MaterialTm(
                        material.getMaterialName(),
                        material.getQty(),
                        material.getName(),
                        material.getDate(),
                        material.getUnitPrice());
                obList.add(tm);
            }
            tblSupplier.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void setCellValueFactor() {
        colMaterialName.setCellValueFactory(new PropertyValueFactory<>("MaterialName"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colSupplierName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
        colTotalPrice.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
    }


    public void txtQty(ActionEvent actionEvent) {


    }

    public void txtUnitPrice(ActionEvent actionEvent) {

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
        String materialName = txtMaterialName.getText();
        int qty = Integer.parseInt(txtQty.getText());
        String supplierName = cmbSupplierName.getValue();
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        Date date = new Date(System.currentTimeMillis());
        int RawMaterialId= Integer.parseInt(lblMaterialId.getText());


        try {
            MaterialRepo.addMaterial(materialName, qty, supplierName, date, unitPrice,RawMaterialId);
            loadAllMaterial();
            clearFields();
            new Alert(Alert.AlertType.INFORMATION,"Material added successfully.");
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,"Failed to add material.");
            e.printStackTrace();
        }
    }

    private void clearFields() {

    }


    public void txtMaterialNameAction(ActionEvent actionEvent) {

    }

    public void cmbSupplierNameAction(ActionEvent actionEvent) {

    }

    public void btnBatch(ActionEvent actionEvent) {

    }

    public void btnAddSupplier(ActionEvent actionEvent) {

    }

    private void getCurrentMaterialId() {
        try {
            String currentId = MaterialRepo.getCurrentId();
            String materialID = generateNextMaterialId(currentId);
            lblMaterialId.setText(materialID);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateNextMaterialId(String currentId) {
        if(currentId != null) {
            int newId = Integer.parseInt(currentId);
            newId++;
            return String.valueOf(newId);
        }
        return "01";

    }
    private void getSupplierName() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> SupplierList = SupplierRepo.getSupplierName();
            for (String s:SupplierList){
                obList.add(s);
            }
            cmbSupplierName.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
