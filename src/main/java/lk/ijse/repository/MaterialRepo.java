package lk.ijse.repository;

import lk.ijse.db.DbConnection;
import lk.ijse.model.Customer;
import lk.ijse.model.Material;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MaterialRepo {

    public static List<Material> getAll() throws SQLException {
        String sql = "SELECT * FROM MaterialManagementView";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        List<Material> materialList = new ArrayList<>();

        while (resultSet.next()) {
            String materialName = resultSet.getString(2);

            int qty = resultSet.getInt(3);
            String supplierName = resultSet.getString(5);

            Date date = resultSet.getDate(6);
            double price = resultSet.getDouble(7);

            Material material = new Material(materialName, qty, supplierName, date, price);
            materialList.add(material);
        }
        return materialList;

    }

    public static String getCurrentId() throws SQLException {
        String sql = "SELECT MAX(RawMaterialID) FROM RawMaterial";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;
    }
    public static void addMaterial(String materialName, int qty, String supplierName, Date date, double unitPrice, int rawMaterialId) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DbConnection.getInstance().getConnection();
            String insertMaterialSql = "INSERT INTO RawMaterial (RawMaterialID, Name, Quantity) VALUES (?, ?, ?)";
            preparedStatement = connection.prepareStatement(insertMaterialSql);
            preparedStatement.setInt(1, rawMaterialId);
            preparedStatement.setString(2, materialName);
            preparedStatement.setInt(3, qty);
            preparedStatement.executeUpdate(); // Execute insert statement

            String insertSupplierDetailSql = "INSERT INTO SupplierDetail (RawMaterialID, SupplierID, Date, Price) " +
                    "VALUES (?, (SELECT SupplierID FROM Supplier WHERE Name = ?), ?, ?)";
            preparedStatement = connection.prepareStatement(insertSupplierDetailSql);
            preparedStatement.setInt(1, rawMaterialId); // Set material ID
            preparedStatement.setString(2, supplierName); // Set supplier name
            preparedStatement.setDate(3, date); // Set date
            preparedStatement.setDouble(4, unitPrice); // Set unit price
            preparedStatement.executeUpdate(); // Execute insert statement
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }
}


