package lk.ijse.repository;

import lk.ijse.db.DbConnection;
import lk.ijse.model.OrderDetail;
import lk.ijse.model.tm.OrderTm;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailRepo {

    public static boolean save(List<OrderDetail> odList) throws SQLException {
        for (OrderDetail od : odList) {
            boolean isSaved = Save(od.getPackageID(), od.getOrderID(), od.getTotalPrice(), od.getQty());
            if (!isSaved) {
                return false;
            }
        }
        return true;
    }

    private static boolean Save(int packageID, int orderID, double totalPrice, int qty) throws SQLException {
        String sql = "INSERT INTO OrderDetail (PackageID, OrderID, Price, Quantity) VALUES (?, ?, ?, ?)";
        PreparedStatement pst = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pst.setInt(1, packageID);
        pst.setInt(2, orderID);
        pst.setDouble(3, totalPrice);
        pst.setInt(4, qty);
        return pst.executeUpdate() > 0;
    }


    public static boolean updateOrderDetail(OrderTm selectedOrder, int packageId) throws SQLException {
        String sql = "UPDATE OrderDetail SET Price = ?, Quantity = ? WHERE OrderID = ? AND PackageID = ?";
        PreparedStatement pst = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pst.setDouble(1, selectedOrder.getTotalPrice());
        pst.setInt(2, selectedOrder.getQty());
        pst.setInt(3, selectedOrder.getOrderID());
        pst.setInt(4, packageId);
        return pst.executeUpdate() > 0;
    }

    public static double calculateTotalPrice(int orderID) throws SQLException {
        List<OrderDetail> orderDetails = getOrderDetailsByOrderID(orderID);
        double totalPrice = 0;
        for (OrderDetail orderDetail : orderDetails) {
            totalPrice += orderDetail.getTotalPrice() * orderDetail.getQty();
        }
        return totalPrice;
    }

    private static List<OrderDetail> getOrderDetailsByOrderID(int orderID) throws SQLException {
        List<OrderDetail> orderDetails = new ArrayList<>();
        String sql = "SELECT * FROM OrderDetail WHERE OrderID = ?";
        PreparedStatement pst = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pst.setInt(1, orderID);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            int packageID = rs.getInt("PackageID");
            double price = rs.getDouble("Price");
            int qty = rs.getInt("Quantity");
            OrderDetail orderDetail = new OrderDetail(packageID, orderID, price, qty);
            orderDetails.add(orderDetail);
        }
        return orderDetails;
    }

}
