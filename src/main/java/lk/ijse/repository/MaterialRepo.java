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

            Date date = resultSet.getDate(7);
            double price = resultSet.getDouble(8);

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
    public static boolean save(Material rawMaterial) throws SQLException {
        String insertRawMaterialSql = "INSERT INTO RawMaterial (RawMaterialID, Name, Quantity) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(insertRawMaterialSql);
            preparedStatement.setObject(1, rawMaterial.getRawMaterialId());
            preparedStatement.setObject(2, rawMaterial.getMaterialName());
            preparedStatement.setObject(3, rawMaterial.getQty());
            return preparedStatement.executeUpdate() > 0;
        }

    public static void updateMaterial(int rawMaterialId, String materialName, int qty, String supplierName, Date date, double price) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        try {
            // Update RawMaterial table
            String updateRawMaterialSql = "UPDATE RawMaterial SET Name=?, Quantity=? WHERE RawMaterialID=?";
            PreparedStatement rawMaterialStatement = connection.prepareStatement(updateRawMaterialSql);
            rawMaterialStatement.setObject(1, materialName);
            rawMaterialStatement.setObject(2, qty);
            rawMaterialStatement.setObject(3, rawMaterialId);
            rawMaterialStatement.executeUpdate();


            String updateSupplierDetailSql = "UPDATE SupplierDetail SET Date=?, Price=? WHERE RawMaterialID=?";
            PreparedStatement supplierDetailStatement = connection.prepareStatement(updateSupplierDetailSql);
            supplierDetailStatement.setObject(1, date);
            supplierDetailStatement.setObject(2, price);
            supplierDetailStatement.setObject(3, rawMaterialId);
            supplierDetailStatement.executeUpdate();

            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public static int getRawMaterialID(String name) throws SQLException {
        String sql = "SELECT RawMaterialID FROM RawMaterial WHERE Name=?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setString(1, name);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return -1;
    }


    public static List<Material> searchMaterials(String supplierName) throws SQLException {
            String sql = "SELECT * FROM MaterialManagementView WHERE SupplierName LIKE ?";
           PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
            preparedStatement.setString(1,supplierName);

            ResultSet resultSet = preparedStatement.executeQuery();
            List<Material> materialList = new ArrayList<>();

            while (resultSet.next()) {
                String name = resultSet.getString(2);
                int qty = resultSet.getInt(3);
                String supplier = resultSet.getString(5);
                Date date = resultSet.getDate(6);
                double price = resultSet.getDouble(7);

                Material material = new Material(name, qty, supplier, date, price);
                materialList.add(material);
            }

            return materialList;
        }
}

