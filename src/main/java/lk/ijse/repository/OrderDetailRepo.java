package lk.ijse.repository;

import lk.ijse.db.DbConnection;
import lk.ijse.model.OrderDetail;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    public static double getTotalPriceByCustomerIdAndOrderId(int customerId, int orderId) throws SQLException {
        String sql = "SELECT SUM(Price) FROM OrderDetail WHERE OrderID = ? AND PackageID IN (SELECT PackageID FROM Orders WHERE CustomerID = ?)";
        PreparedStatement pst = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pst.setInt(1, orderId);
        pst.setInt(2, customerId);
        ResultSet resultSet = pst.executeQuery();
        if (resultSet.next()) {
            return resultSet.getDouble(1);
        } else {
            return 0;
        }
    }
}
