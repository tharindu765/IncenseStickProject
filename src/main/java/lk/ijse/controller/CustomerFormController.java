package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import lk.ijse.Util.Regex;
import lk.ijse.model.Customer;
import lk.ijse.model.tm.CustomerTm;
import lk.ijse.repository.CustomerRepo;

import java.sql.SQLException;
import java.util.List;

public class CustomerFormController {

    public TextField txtID;
    public TextField txtName;
    public TextField txtTel;
    public TextField txtAddress;
    public TableView<CustomerTm> tblCustomer;
    public TableColumn<?,?> colID;
    public TableColumn<?,?> colName;
    public TableColumn<?,?> colTel;
    public TableColumn<?,?> colAddress;

    public void initialize(){
        setCellValueFactor();
        loadAllCustomer();
        tblCustomer.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {

                txtID.setText(newSelection.getCustomerID());
                 txtName.setText(newSelection.getName());
                 txtAddress.setText(newSelection.getAddress());
                txtTel.setText(newSelection.getTelNumber());
            }

    });
        }

    private void loadAllCustomer() {
        ObservableList<CustomerTm> obList = FXCollections.observableArrayList();
    try{
        List<Customer> customerList = CustomerRepo.getAll();
        for(Customer customer:customerList){
            CustomerTm tm = new CustomerTm(
                    customer.getCustomerID(),
                    customer.getAddress(),
                    customer.getTelNumber(),
                    customer.getName());
            obList.add(tm);
        }
        tblCustomer.setItems(obList);
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }

    }

    private void setCellValueFactor() {
        colID.setCellValueFactory(new PropertyValueFactory<>("CustomerID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colTel.setCellValueFactory(new PropertyValueFactory<>("telNumber"));
    }


    public void btnDelete(ActionEvent actionEvent) {
    String id = txtID.getText();
    try{
        boolean isDeleted = CustomerRepo.delete(id);
        if(isDeleted){
            loadAllCustomer();
            clearFields();
            new Alert(Alert.AlertType.CONFIRMATION,"Customer deleted").show();
        }
    }catch (SQLException e){
     new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
    }
    }

    public void btnUpdate(ActionEvent actionEvent) {
    String id = txtID.getText();
    String name = txtName.getText();
    String address = txtAddress.getText();
    String tel = txtTel.getText();

        if (txtAddress.getText().isEmpty() || txtID.getText().isEmpty() || txtTel.getText().isEmpty() || txtName.getText().isEmpty()){
            new Alert(Alert.AlertType.ERROR,"Please fill the data to Update").show();
            return;
        }
    Customer customer = new Customer(id,name,address,tel);

    try{
        boolean isUpdated = CustomerRepo.update(customer);
        if(isUpdated){
            loadAllCustomer();
            clearFields();
         new Alert(Alert.AlertType.CONFIRMATION,"Customer Updated").show();
        }
    }catch (SQLException e){
        new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
    }

    }

    public void btnAdd(ActionEvent actionEvent) {

        String id = txtID.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String tel = txtTel.getText();
        Customer customer = new Customer(id, name, address, tel);
if (txtAddress.getText().isEmpty() || txtID.getText().isEmpty() || txtTel.getText().isEmpty() || txtName.getText().isEmpty()){
    new Alert(Alert.AlertType.ERROR,"Please fill the data to Add").show();
    return;
}
        if (!isValied()) {
            try {
                boolean isSaved = CustomerRepo.save(customer);
                if (isSaved) {
                    loadAllCustomer();
                    new Alert(Alert.AlertType.CONFIRMATION, "Customer saved").show();
                    clearFields();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private void clearFields() {
        txtID.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtTel.setText("");
    }

    public void btnSearch(ActionEvent actionEvent) throws SQLException {
    String id = txtID.getText();
    Customer customer = CustomerRepo.searchById(id);
    if (customer != null){
    txtID.setText(customer.getCustomerID());
    txtName.setText(customer.getName());
    txtTel.setText(customer.getTelNumber());
    txtAddress.setText(customer.getAddress());

}else{
        new Alert(Alert.AlertType.INFORMATION,"Customer not found").show();
    }
    }

    public void btnReset(ActionEvent actionEvent) {
    txtID.setText("");
    txtName.setText("");
    txtAddress.setText("");
    txtTel.setText("");
    }

    public void telReleaseAction(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.Util.TextField.TELNUMBER,txtTel);
    }

    public boolean isValied(){
        if (!Regex.setTextColor(lk.ijse.Util.TextField.TELNUMBER,txtID)) return false;
        if (!Regex.setTextColor(lk.ijse.Util.TextField.NAME,txtName)) return false;
        //if(!Regex.setTextColor(lk.ijse.Util.TextField.ADDRESS,txtAddress)) return false;
        if (!Regex.setTextColor(lk.ijse.Util.TextField.NIC,txtID)) return false;
        return true;
    }

    public void txtNameRelease(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.Util.TextField.NAME,txtName);
    }

    public void txtAddressRelese(KeyEvent keyEvent) {
        //Regex.setTextColor(lk.ijse.Util.TextField.ADDRESS,txtAddress);
    }

    public void txtNICRelese(KeyEvent keyEvent) {
    Regex.setTextColor(lk.ijse.Util.TextField.NIC,txtID);
    }
}
