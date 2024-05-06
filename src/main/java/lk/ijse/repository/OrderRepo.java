package lk.ijse.repository;

import lk.ijse.db.DbConnection;
import lk.ijse.model.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderRepo {

    public static List<Order> getAll() throws SQLException {
        String sql = "SELECT * FROM OrderDetailView;";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        List<Order> orderList = new ArrayList<>();
        while (resultSet.next()){
            int orderId = resultSet.getInt(1);
            Date date =resultSet.getDate(2);
            String incenseType =resultSet.getString(3);
            String customerName =resultSet.getString(4);
            int qty =resultSet.getInt(5);
            double price=resultSet.getDouble(6);
            Order order =new Order(date,incenseType,customerName,qty,price,orderId);
            orderList.add(order);
        }
        return orderList;
    }

    public static boolean save(Order order) throws SQLException {
        String sql = "INSERT INTO Orders (OrderId, Date, CustomerId) VALUES (?, ?, ?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setObject(1, order.getOrderId());
        System.out.println(order.getOrderId());
        pstm.setObject(2, order.getDate());
        pstm.setObject(3, order.getCustomerId());
        System.out.println(order.getCustomerId());
        return pstm.executeUpdate() > 0;
    }


    public static String getCurrentId() throws SQLException {
        String sql = "SELECT OrderID FROM Orders ORDER BY OrderID DESC LIMIT 1";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            String orderId = resultSet.getString(1);
            return orderId;
        }
        return null;
    }

    public static boolean addOrder(String orderId, Date date, String customerName) throws SQLException {
        String sql = "INSERT INTO Orders (OrderID, Date, CustomerID) VALUES (?, ?, (SELECT CustomerID FROM Customer WHERE Name = ?))";
        PreparedStatement pst = DbConnection.getInstance().getConnection().prepareStatement(sql);
            pst.setString(1, orderId);
            pst.setDate(2, date);
            pst.setString(3, customerName);
        return pst.executeUpdate() > 0;
    }
    public static List<Order> searchById(String cuID) throws SQLException {
        String sql = "SELECT * FROM OrderDetailView WHERE CustomerName = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setString(1, CustomerRepo.setCustomerName(cuID));
        ResultSet resultSet = pstm.executeQuery();
        List<Order> orderList = new ArrayList<>();

        while (resultSet.next()) {
            Date date = resultSet.getDate(1);
            String incenseType = resultSet.getString(2);
            String customerName = resultSet.getString(3);
            int qty = resultSet.getInt(4);
            double price = resultSet.getDouble(5);
            Order order = new Order(date, incenseType, customerName, qty, price);
            orderList.add(order);
        }
        return orderList;
    }

    public static List<String> getOrderId(String cuID) throws SQLException {
       String sql = "select OrderID from Orders where CustomerID = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        List<String> OBList = new ArrayList<>();
        pstm.setObject(1,cuID);
        System.out.println(cuID);
        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()) {
            String OB = String.valueOf(resultSet.getInt(1));
            OBList.add(OB);
        }
        return OBList;
    }
}

