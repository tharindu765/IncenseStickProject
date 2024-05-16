package lk.ijse.repository;

import lk.ijse.db.DbConnection;
import lk.ijse.model.Supplier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierRepo {
    public static List<String> getSupplierName() throws SQLException, SQLException {
        String sql = "SELECT Name FROM Supplier";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        List<String> supplierNames = new ArrayList<>();

        while (resultSet.next()) {
            String supplierName = resultSet.getString("Name");
            supplierNames.add(supplierName);
        }

        return supplierNames;
    }

    public static List<Supplier> getAll() throws SQLException {
        String sql = "SELECT * FROM Supplier";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        List<Supplier> suppliers = new ArrayList<>();

        while (resultSet.next()) {
            String supplierID = resultSet.getString("SupplierID");
            String name = resultSet.getString("Name");
            String telNumber = resultSet.getString("TelNumber");

            Supplier supplier = new Supplier(supplierID, name, telNumber);
            suppliers.add(supplier);
        }

        return suppliers;
    }

    public static void addSupplier(String name, String tel, String NIC) throws SQLException {
        String sql = "INSERT INTO Supplier (Name, TelNumber,SupplierID) VALUES (?, ?,?)";
        PreparedStatement pst = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pst.setString(1, name);
        pst.setString(2, tel);
        pst.setObject(3, NIC);

        pst.executeUpdate();
    }

    public static Supplier searchSupplierById(String keyword) throws SQLException {
        String sql = "SELECT * FROM Supplier WHERE SupplierID = ?";
        PreparedStatement pst = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pst.setString(1, keyword);

        ResultSet resultSet = pst.executeQuery();

        if (resultSet.next()) {
            String supplierID = resultSet.getString("SupplierID");
            String name = resultSet.getString("Name");
            String telNumber = resultSet.getString("TelNumber");

            return new Supplier(supplierID, name, telNumber);
        }

        return null;
    }

    public static void updateSupplier(String supplierID, String name, String tel, String nic) throws SQLException {
        String sql = "UPDATE Supplier SET Name = ?, TelNumber = ?, SupplierID = ? WHERE SupplierID = ?";
        PreparedStatement pst = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pst.setString(1, name);
        pst.setString(2, tel);
        pst.setString(3, String.valueOf(nic));
        pst.setString(4, String.valueOf(supplierID));

        pst.executeUpdate();
    }

    public static void deleteSupplier(String supplierID) throws SQLException {
        String sql = "DELETE FROM Supplier WHERE SupplierID = ?";
        PreparedStatement pst = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pst.setString(1, String.valueOf(supplierID));
        pst.executeUpdate();
    }

    public static int getTotalSupplierCount() throws SQLException {
            Connection connection = DbConnection.getInstance().getConnection();
            String sql = "SELECT COUNT(*) AS TotalSuppliers FROM Supplier";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getInt("TotalSuppliers");
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return 0;
        }
}