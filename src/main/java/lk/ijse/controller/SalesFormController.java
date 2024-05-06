package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.model.Sale;
import lk.ijse.model.tm.SaleTm;
import lk.ijse.repository.CustomerRepo;
import lk.ijse.repository.OrderDetailRepo;
import lk.ijse.repository.OrderRepo;
import lk.ijse.repository.SaleRepo;

import java.sql.SQLException;
import java.util.List;

public class SalesFormController {
    public TableColumn<?,?> colOrderID;
    public TableColumn<?,?> colName;
    public TableColumn<?,?> colPackageType;
    public TableColumn<?,?> colDate;
    public TableColumn<?,?> colStatus;
    public TableColumn<?,?> colQty;
    public TableView<SaleTm> tblSale;
    public JFXComboBox<String> cmbCustomer;
    public JFXComboBox<String> cmbStatus;
    public Label lblOrderID;
    public Label lblTotalPrice;
    public Label lblOrderIDate;
    public Label lblTranslationID;
    public JFXComboBox<String> cmbOrderID;
    public JFXButton btnUpdate;
    public TextField txtNIC;

    public void initialize(){
        setcmbOrderID();
        LoadAllSale();
        setCellValueFactor();
        setCmbStatus();
        getCurrentTransactionId();

    }

    private void setcmbOrderID() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        System.out.println(txtNIC.getText());
        try{
            List<String> OBlist = OrderRepo.getOrderId(txtNIC.getText());
            for (String i:OBlist){
                obList.add(i);
            }
            cmbOrderID.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void setCellValueFactor() {
        colOrderID.setCellValueFactory(new PropertyValueFactory<>("orderID"));
        colPackageType.setCellValueFactory(new PropertyValueFactory<>("pType"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("sDate"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
    }

    private void LoadAllSale() {
        ObservableList<SaleTm> obList = FXCollections.observableArrayList();
        try {
            List<Sale> saleList = SaleRepo.getAll();
            for (Sale s :saleList){
                SaleTm tm = new SaleTm(s.getOrderID(),s.getPType(),s.getSDate(),s.getStatus(),s.getQty());
                obList.add(tm);
            }
            tblSale.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void getCurrentTransactionId() {
        try {
            String currentId = SaleRepo.getCurrentId();
            String nextTransationId = generateNextTransationId(currentId);
            lblTranslationID.setText(nextTransationId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateNextTransationId(String currentId) {
        if(currentId != null) {
            int newId = Integer.parseInt(currentId);
            newId++;
            return String.valueOf(newId);
        }
        return "01";

    }

    public void btnUpdate(ActionEvent actionEvent) {
        int OID = Integer.parseInt(cmbOrderID.getValue());
        String status = cmbStatus.getValue();
        try {
            SaleRepo.updateStatusByOrderIDAndCustomerID(String.valueOf(OID),status);
            tblSale.refresh();
            LoadAllSale();
            setCellValueFactor();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setCmbStatus(){
        ObservableList<String> status = FXCollections.observableArrayList("Pending","Paid");

        cmbStatus.setItems(status);
    }

    public void cmbOrderID(ActionEvent actionEvent) {
        int ID = Integer.parseInt(txtNIC.getText());
        int OID = Integer.parseInt(cmbOrderID.getValue());
        try {
            double totalPrice = OrderDetailRepo.getTotalPriceByCustomerIdAndOrderId(ID,OID);
            lblTotalPrice.setText(String.valueOf(totalPrice));
            String St = SaleRepo.getStatusByOrderIDAndCustomerID(cmbOrderID.getValue(), txtNIC.getText());

            cmbStatus.setValue(St);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void txtNICAction(ActionEvent actionEvent) {
        ObservableList<SaleTm> obList = FXCollections.observableArrayList();
        String NIC = txtNIC.getText();
        try{
            List<Sale> saleList = SaleRepo.searchSalesByCustomerID(NIC);
            for (Sale s :saleList){
            SaleTm tm = new SaleTm(s.getOrderID(),s.getPType(),s.getSDate(),s.getStatus(),s.getQty());
            obList.add(tm);
        }
        tblSale.setItems(obList);
            setcmbOrderID();
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
    }
}

