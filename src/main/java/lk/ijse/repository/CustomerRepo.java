package lk.ijse.repository;

import com.jfoenix.controls.JFXComboBox;
import lk.ijse.db.DbConnection;
import lk.ijse.model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepo {

    public static List<Customer> getAll() throws SQLException {
        String sql = "SELECT * FROM Customer";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        List<Customer> customerList = new ArrayList<>();

        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String name = resultSet.getString(3);
            String address = resultSet.getString(2);
            String tel = resultSet.getString(4);

            Customer customer = new Customer(id, name, address, tel);
            customerList.add(customer);
        }
        return customerList;
    }

    public static boolean save(Customer customer) throws SQLException {
        String sql = "insert into Customer values(?,?,?,?)";
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, customer.getCustomerID());
        pstm.setObject(2, customer.getName());
        pstm.setObject(3, customer.getTelNumber());
        pstm.setObject(4, customer.getAddress());
        return pstm.executeUpdate() > 0;
    }

    public static boolean delete(String id) throws SQLException {
        String sql = "delete from Customer where CustomerID = ?";
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);
        return pstm.executeUpdate() > 0;
    }

    public static boolean update(Customer customer) throws SQLException {
        String sql = "update Customer set CustomerID = ?,Name = ?, TelNumber = ?,Address = ? ";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1, customer.getCustomerID());
        pstm.setObject(2, customer.getName());
        pstm.setObject(3, customer.getTelNumber());
        pstm.setObject(4, customer.getAddress());

        return pstm.executeUpdate() > 0;
    }

    public static Customer searchById(String id) throws SQLException {
        String sql = "Select * from Customer where CustomerID = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1, id);
        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String Cid = resultSet.getString(1);
            String name = resultSet.getString(2);
            String tel = resultSet.getString(3);
            String address = resultSet.getString(4);
            Customer customer = new Customer(Cid, name, tel, address);
            return customer;
        }
        return null;
    }

    public static boolean exitCustomer(String name) throws SQLException {
        String sql = "SELECT CustomerID FROM Customer WHERE Name = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setString(1, name);
        ResultSet resultSet = pstm.executeQuery();
        return resultSet.next();
    }

    public static int getCustomerID(String name) throws SQLException {
        String sql = "select CustomerID from Customer where Name = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1, name);
        ResultSet resultSet = pstm.executeQuery();
        resultSet.next();
        int Id = resultSet.getInt(1);

        return Id;
    }

    public static List<String> getCustomerID() throws SQLException {
        String sql = "select CustomerID from Customer";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        List<String> IDList = new ArrayList<>();
        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()) {
            String ID = String.valueOf(resultSet.getInt(1));
            IDList.add(ID);
        }
        return IDList;
    }

    public static String setCustomerName(String id) throws SQLException {
        String sql = "select Name from Customer where CustomerID = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1, id);
        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            return resultSet.getString(1);
        } else {
            return null;
        }

    }
}