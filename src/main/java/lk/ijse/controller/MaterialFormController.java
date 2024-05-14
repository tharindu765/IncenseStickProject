package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.model.*;
import lk.ijse.model.tm.CustomerTm;
import lk.ijse.model.tm.MaterialTm;
import lk.ijse.model.tm.OrderTm;
import lk.ijse.repository.*;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public TableColumn<?,?> colAction;
    public DatePicker datePicker;

    private ObservableList<MaterialTm> cart = FXCollections.observableArrayList();
    public void initialize(){
        setCellValueFactor();
        loadAllMaterial();
        getCurrentMaterialId();
        getSupplierName();

        tblSupplier.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {

                txtMaterialName.setText(newSelection.getMaterialName());
                txtQty.setText(String.valueOf(newSelection.getQty()));
                cmbSupplierName.setValue(newSelection.getSupplierName());
                datePicker.setValue(newSelection.getDate().toLocalDate());
                txtUnitPrice.setText(String.valueOf(newSelection.getUnitPrice()));
            }
        });
    }

    private void loadAllMaterial() {
        ObservableList<MaterialTm> spList = FXCollections.observableArrayList();
        try{
            List<Material> materialList = MaterialRepo.getAll();
            for(Material material:materialList){
                MaterialTm tm = new MaterialTm(
                        material.getMaterialName(),
                        material.getQty(),
                        material.getName(),
                        material.getDate(),
                        material.getUnitPrice());
                spList.add(tm);
            }
            tblSupplier.setItems(spList);
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
        colAction.setCellValueFactory(new PropertyValueFactory<>("remove"));
    }


    public void txtQty(ActionEvent actionEvent) {


    }

    public void txtUnitPrice(ActionEvent actionEvent) {

    }

    public void btnUpdate(ActionEvent actionEvent) {
        // Get the selected item from the table
        MaterialTm selectedMaterial = tblSupplier.getSelectionModel().getSelectedItem();
        if (selectedMaterial == null) {
            new Alert(Alert.AlertType.WARNING, "Please select a material to update.").show();
            return;
        }
        System.out.println(txtQty.getText());
        System.out.println(selectedMaterial.getRawMaterialId());
        try {
            MaterialRepo.updateMaterial(
                    MaterialRepo.getRawMaterialID(txtMaterialName.getText()),
                    txtMaterialName.getText(),
                    Integer.parseInt(txtQty.getText()),
                    cmbSupplierName.getValue(),
                    Date.valueOf(datePicker.getValue()),
                    Double.parseDouble(txtUnitPrice.getText()));

            loadAllMaterial();
            new Alert(Alert.AlertType.INFORMATION, "Material updated successfully.").show();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to update material: " + e.getMessage()).show();
        }
    }

    public void btnDelete(ActionEvent actionEvent) {

    }

    public void btnReset(ActionEvent actionEvent) {
clearFields();
loadAllMaterial();
    }
    public void brnSearch(ActionEvent actionEvent) {
        String supplierName = cmbSupplierName.getValue();

        try {
            List<Material> searchResult = MaterialRepo.searchMaterials(supplierName);
            ObservableList<MaterialTm> searchResultList = FXCollections.observableArrayList();
            for (Material material : searchResult) {
                MaterialTm tm = new MaterialTm(material.getMaterialName(),
                        material.getQty(),
                        material.getName(),
                        material.getDate(),
                        material.getUnitPrice());
                searchResultList.add(tm);
            }
            tblSupplier.setItems(searchResultList);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to search materials: " + e.getMessage()).show();
        }
    }
    public void btnAdd(ActionEvent actionEvent) {
        /*for (MaterialTm material : cart) {
            try {
                MaterialRepo.addMaterial(material.getMaterialName(), material.getQty(),
                        material.getName(), material.getDate(), material.getUnitPrice(),
                        material.getRawMaterialId());
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Failed to add material.").show();
                e.printStackTrace();
            }
        }
        cart.clear();
        loadAllMaterial();
        new Alert(Alert.AlertType.INFORMATION, "Materials added successfully.").show();
        */
        String materialName = txtMaterialName.getText();
        int qty = Integer.parseInt(txtQty.getText());
        String supplierName = cmbSupplierName.getValue();
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        Date date = Date.valueOf(datePicker.getValue());
        int rawMaterialId = Integer.parseInt(lblMaterialId.getText());

        var material = new Material(rawMaterialId,materialName,qty);

        List<SupplierDetail> spmList = new ArrayList<>();

        for (int i = 0; i < tblSupplier.getItems().size(); i++) {
            MaterialTm tm = cart.get(i);
            SupplierDetail supplierDetail = new SupplierDetail(
                            tm.getRawMaterialId(),
                            tm.getSupplierName(),
                            tm.getDate(),
                            tm.getUnitPrice()
);
            spmList.add(supplierDetail);
        }
        PlaceMaterial pm = new PlaceMaterial(material,spmList);

        try {
            boolean isPlaced = PlaceMaterialRepo.placeMaterial(pm);
            if(isPlaced) {
                tblSupplier.getItems().clear();
                getCurrentMaterialId();
                clearFields();
                cmbSupplierName.setDisable(false);
                new Alert(Alert.AlertType.CONFIRMATION, "Material Placed!").show();
            } else {
                new Alert(Alert.AlertType.WARNING, "Material Placed Unsuccessfully!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void clearFields() {
        txtMaterialName.clear();
        txtQty.clear();
        cmbSupplierName.getSelectionModel().clearSelection();
        txtUnitPrice.clear();
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
    public void btnAddToCart(ActionEvent actionEvent) {
        String materialName = txtMaterialName.getText();
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        int rawMaterialId = Integer.parseInt(lblMaterialId.getText());
        String supplierName = cmbSupplierName.getValue();
        int qty = Integer.parseInt(txtQty.getText());
        Date date = Date.valueOf(datePicker.getValue());

        JFXButton remove = new JFXButton("❌");
        remove.setCursor(Cursor.HAND);
        remove.setStyle("-fx-text-fill: red;");
        remove.setOnAction((e) -> {
            ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);
            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();
            if (type.orElse(no) == yes) {
                int selectedIndex = tblSupplier.getSelectionModel().getSelectedIndex();
                cart.remove(selectedIndex);
                if (cart.isEmpty()) {
                    cmbSupplierName.setDisable(false);
                }
                    tblSupplier.refresh();
            }
        });


        if (!tblSupplier.getItems().isEmpty()) {

            for (int i = 0; i <  cart.size(); i++) {
                String colMaterialNameCellData = (String) colMaterialName.getCellData(i);
                int colQuantity = (int) colQty.getCellData(i);
                String supplierNAme = (String) colSupplierName.getCellData(i);
                System.out.println(supplierNAme);
                System.out.println(supplierName);
                Date date1 = (Date) colDate.getCellData(i);
                if (colMaterialNameCellData.equals(String.valueOf(materialName))) {
                    MaterialTm tm = cart.get(i);
                    qty += tm.getQty();
                    tm.setQty(qty);
                    tblSupplier.refresh();
                    return;
                }
            }
        }
        cmbSupplierName.setDisable(true);

        MaterialTm tm = new MaterialTm(materialName, qty, supplierName, date, unitPrice, remove, rawMaterialId, supplierName);
        cart.add(tm);
        tblSupplier.setItems(cart);
    }

}
