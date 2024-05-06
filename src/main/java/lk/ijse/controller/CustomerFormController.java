package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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

    Customer customer = new Customer(id,name,address,tel);

    try{
        boolean isUpdated = CustomerRepo.update(customer);
        if(isUpdated){
            loadAllCustomer();
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
        Customer customer =new Customer(id,name,address,tel);

        try {
            boolean isSaved = CustomerRepo.save(customer);
            if (isSaved){
                loadAllCustomer();
                new Alert(Alert.AlertType.CONFIRMATION,"Customer saved").show();
                clearFields();
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
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
}
