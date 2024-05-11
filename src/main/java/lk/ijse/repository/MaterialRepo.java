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
        Connection connection = DbConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        try {
           
            String insertRawMaterialSql = "INSERT INTO RawMaterial (RawMaterialID, Name, Quantity) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertRawMaterialSql);
            try {
                preparedStatement.setInt(1, rawMaterialId);
                preparedStatement.setString(2, materialName);
                preparedStatement.setInt(3, qty);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


            String insertSupplierDetailSql = "INSERT INTO SupplierDetail (RawMaterialID, SupplierID, Date, Price) VALUES (?, (SELECT SupplierID FROM Supplier WHERE Name = ?), ?, ?)";
            PreparedStatement preparedStatement1 = connection.prepareStatement(insertSupplierDetailSql);
            try  {
                preparedStatement1.setInt(1, rawMaterialId);
                preparedStatement1.setString(2, supplierName);
                preparedStatement1.setDate(3, date);
                preparedStatement1.setDouble(4, unitPrice);
                preparedStatement1.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw new RuntimeException(e);
        } finally {
            connection.setAutoCommit(true);

        }
    }

}


