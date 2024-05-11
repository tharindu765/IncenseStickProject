package lk.ijse.repository;

import javafx.scene.control.DatePicker;
import lk.ijse.db.DbConnection;
import lk.ijse.model.Sale;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SaleRepo {
    public static List<Sale> getAll() throws SQLException {
String sql = "select * from SaleDetails";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();
        List<Sale> saleList = new ArrayList<>();
        while (resultSet.next()){
            int transationID = resultSet.getInt(1);
            int qty = resultSet.getInt(2);
            Date sDate = resultSet.getDate(3);
            int orderID = resultSet.getInt(4);
            Date oDate =resultSet.getDate(5);
            String customerName = resultSet.getString(7);
            String status = resultSet.getString(8);
            String pType = resultSet.getString(9);
            Sale sale = new Sale(transationID,qty,sDate,orderID,oDate,customerName,status,pType);
            saleList.add(sale);
        }
        return saleList;
    }

    public static String getCurrentId() throws SQLException {
        String sql = "SELECT TransactionID FROM Sale ORDER BY OrderID DESC LIMIT 1";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;
    }

    public static boolean addOrderSale(String transationID, String qty, String orderId) throws SQLException {
        String sql = "INSERT INTO Sale values(?,?,?,?,?)";
        PreparedStatement pst = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pst.setString(1, transationID);
        pst.setString(2, qty);
        LocalDate now = LocalDate.now();
        pst.setString(3, String.valueOf(now));
        pst.setObject(4,orderId);
        pst.setObject(5,"Pending");
        return pst.executeUpdate()> 0;
    }

    public static boolean addOrderSale(Sale sale) throws SQLException {
        String sql = "INSERT INTO Sale values(?,?,?,?,?)";
        PreparedStatement pst = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pst.setString(1, String.valueOf(sale.getTransactionID()));
        pst.setString(2, String.valueOf(sale.getQty()));
        LocalDate now = LocalDate.now();
        pst.setString(3, String.valueOf(now));
        pst.setObject(4,sale.getOrderID());
        pst.setObject(5,"Pending");
        return pst.executeUpdate()> 0;

    }

    public static List<Sale> searchSalesByCustomerID(String customerID) throws SQLException {
        String sql = "SELECT * FROM SaleDetails WHERE customerID = ?";
        PreparedStatement pst = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pst.setString(1, customerID);

        ResultSet resultSet = pst.executeQuery();
        List<Sale> saleList = new ArrayList<>();
        while (resultSet.next()) {
            int transationID = resultSet.getInt(1);
            int qty = resultSet.getInt(2);
            Date sDate = resultSet.getDate(3);
            int orderID = resultSet.getInt(4);
            Date oDate = resultSet.getDate(5);
            String customerName = resultSet.getString(7);
            String status = resultSet.getString(8);
            String pType = resultSet.getString(9);

            Sale sale = new Sale(transationID, qty, sDate, orderID, oDate, customerName, status, pType);
            saleList.add(sale);
        }
        return saleList;
    }
    public static String getStatusByOrderIDAndCustomerID(String orderID, String customerID) throws SQLException {
        String sql = "SELECT PaymentStatus FROM SaleDetails WHERE OrderID = ? AND CustomerID = ?";
        PreparedStatement pst = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pst.setString(1, orderID);
        pst.setString(2, customerID);

        ResultSet resultSet = pst.executeQuery();
        if (resultSet.next()) {
            return resultSet.getString("PaymentStatus");
        } else {
            return null;
        }
    }
    public static boolean updateStatusByOrderIDAndCustomerID(String orderID, String newStatus) throws SQLException {
        String sql = "UPDATE Sale SET PaymentStatus = ? WHERE OrderID = ?";
        PreparedStatement pst = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pst.setString(1, newStatus);
        pst.setString(2, orderID);

        return pst.executeUpdate() > 0;
    }

    public static boolean updateSaleQuantity(int orderID, int qty) throws SQLException {
        String sql = "UPDATE Sale SET Quantity = ? WHERE OrderID = ?";
        PreparedStatement pst = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pst.setInt(1, qty);
        pst.setInt(2, orderID);

        return pst.executeUpdate() > 0;
    }

}

