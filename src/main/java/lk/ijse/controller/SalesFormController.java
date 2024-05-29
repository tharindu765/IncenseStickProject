package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import lk.ijse.Util.Regex;
import lk.ijse.db.DbConnection;
import lk.ijse.model.Sale;
import lk.ijse.model.tm.SaleTm;
import lk.ijse.model.tm.SupplierTm;
import lk.ijse.repository.CustomerRepo;
import lk.ijse.repository.OrderDetailRepo;
import lk.ijse.repository.OrderRepo;
import lk.ijse.repository.SaleRepo;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SalesFormController {
    public TableColumn<?,?> colOrderID;

    public TableColumn<?,?> colPackageType;
    public TableColumn<?,?> colDate;
    public TableColumn<?,?> colStatus;
    public TableColumn<?,?> colQty;
    public TableView<SaleTm> tblSale;

    public JFXComboBox<String> cmbStatus;

    public Label lblTotalPrice;

    public Label lblTranslationID;
    public JFXComboBox<String> cmbOrderID;

    public JFXButton btnUpdate;
    public TextField txtNIC;

    public void initialize(){
        LoadAllSale();
        setCellValueFactor();
        setCmbStatus();
        getCurrentTransactionId();
        setcmbOrderID();
        cmbOrderID.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {

                try {
                    double t = OrderDetailRepo.calculateTotalPrice(Integer.parseInt(newValue));
                    lblTotalPrice.setText(String.valueOf(t));
                    txtNIC.setText(OrderRepo.getCustomerIDFromOrderID(newValue));
                    String saleStatus = SaleRepo.getSaleStatusByOrderID(newValue);
                    cmbStatus.setValue(saleStatus);
                } catch (SQLException e) {
                    new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
                }
            }
        });
        tblSale.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                SaleTm sale = newSelection;
                try {
                    txtNIC.setText(CustomerRepo.getCustomerID(sale.getOrderID()));
                    cmbStatus.setValue(sale.getStatus());
                    cmbOrderID.setValue(String.valueOf(sale.getOrderID()));
                    //lblTotalPrice.setText();
                    double t = OrderDetailRepo.calculateTotalPrice(Integer.parseInt(String.valueOf(sale.getOrderID())));
                    lblTotalPrice.setText(String.valueOf(t));
                } catch (SQLException e) {
                    new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
                }
            }
        });
    }

    private void setcmbOrderID() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try{
            List<String> OBlist = OrderRepo.getOrderId();
            for (String i:OBlist){
                obList.add(i);
            }
            cmbOrderID.setItems(obList);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

    }

    private void setCellValueFactor() {
        colOrderID.setCellValueFactory(new PropertyValueFactory<>("orderID"));
        //colPackageType.setCellValueFactory(new PropertyValueFactory<>("pType"));
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
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    private void getCurrentTransactionId() {
        try {
            String currentId = SaleRepo.getCurrentId();
            String nextTransationId = generateNextTransationId(currentId);
            lblTranslationID.setText(nextTransationId);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
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
        if (!isValied()) {
            int OID = Integer.parseInt(cmbOrderID.getValue());
            String status = cmbStatus.getValue();
            try {
                SaleRepo.updateStatusByOrderIDAndCustomerID(String.valueOf(OID), status);
                tblSale.refresh();
                LoadAllSale();
                setCellValueFactor();
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            }
        }
    }
    public void setCmbStatus(){
        ObservableList<String> status = FXCollections.observableArrayList("Pending","Paid");

        cmbStatus.setItems(status);
    }

    public void cmbOrderID(ActionEvent actionEvent) {

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
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
    }
    }

    public void btnBill(ActionEvent actionEvent) throws JRException, SQLException {
        JasperDesign jasperDesign = JRXmlLoader.load("src/main/resources/report/Bill.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        Map<String,Object> data = new HashMap<>();
        String OID = cmbOrderID.getValue();
        data.put("ID",OID);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, data, DbConnection.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint,false);
    }

    public void txtNICReleceAction(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.Util.TextField.NIC,txtNIC);
    }
    public boolean isValied() {
        if (!Regex.setTextColor(lk.ijse.Util.TextField.NIC, txtNIC)) return false;
    return true;
    }

    }

