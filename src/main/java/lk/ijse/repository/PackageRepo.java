package lk.ijse.repository;

import lk.ijse.db.DbConnection;
import lk.ijse.model.tm.PackageTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PackageRepo {
    public static List<PackageTm> getAllPackageDetails() throws SQLException {
        String sql = "SELECT * FROM PackageDetails";
        Connection connection = DbConnection.getInstance().getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<PackageTm> packageList = new ArrayList<>();

            while (resultSet.next()) {
                int packageID = resultSet.getInt("PackageID");
                String description = resultSet.getString("Description");
                double unitPrice = resultSet.getDouble("UnitPrice");
                int quantity = resultSet.getInt("Quantity");
                int batchID = resultSet.getInt("BatchID");

                PackageTm packageTm = new PackageTm(packageID, description, unitPrice, quantity, batchID);
                packageList.add(packageTm);
            }

            return packageList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getCurrentId() throws SQLException {
        String sql = "SELECT MAX(PackageID) AS MaxID FROM IncensePackage";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String PackageId = resultSet.getString(1);
            return PackageId;
        }
        return null;
    }

    public static boolean addPackage(String description, double unitPrice, int quantity, String batchID, int packageID) throws SQLException {
        String sql1 = "INSERT INTO IncensePackage (PackageID, Description, UnitPrice, Quantity) VALUES (?, ?, ?, ?)";
        String sql2 = "INSERT INTO PackageDetail (PackageID, BatchID) VALUES (?, ?)";

        Connection connection = DbConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        try {
            PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
            preparedStatement1.setInt(1, packageID);
            preparedStatement1.setString(2, description);
            preparedStatement1.setDouble(3, unitPrice);
            preparedStatement1.setInt(4, quantity);

            int affectedRows1 = preparedStatement1.executeUpdate();

            if (affectedRows1 > 0) {
                PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
                preparedStatement2.setInt(1, packageID);
                preparedStatement2.setInt(2, Integer.parseInt(batchID));

                int affectedRows2 = preparedStatement2.executeUpdate();

                if (affectedRows2 > 0) {
                    connection.commit();
                    return true;
                }
            }

            connection.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.setAutoCommit(true);
        }

        return false;
    }

    public static List<PackageTm> searchPackagesByBatchID(String batchID) throws SQLException {
        String sql = "SELECT * FROM PackageDetails WHERE BatchID = ?";
        Connection connection = DbConnection.getInstance().getConnection();
        List<PackageTm> packageList = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        try {
            preparedStatement.setString(1, batchID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int packageID = resultSet.getInt("PackageID");
                String description = resultSet.getString("Description");
                double unitPrice = resultSet.getDouble("UnitPrice");
                int quantity = resultSet.getInt("Quantity");

                PackageTm packageTm = new PackageTm(packageID, description, unitPrice, quantity, Integer.parseInt(batchID));
                packageList.add(packageTm);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return packageList;
    }

    public static boolean updatePackage(int packageID, String description, double unitPrice, int quantity, String batchID) throws SQLException {
        String sql = "UPDATE IncensePackage SET Description=?, UnitPrice=?, Quantity=? WHERE PackageID=?";
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        try {
            preparedStatement.setString(1, description);
            preparedStatement.setDouble(2, unitPrice);
            preparedStatement.setInt(3, quantity);
            preparedStatement.setInt(4, packageID);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static boolean deletePackage(int packageID) throws SQLException {
        String sql = "DELETE FROM IncensePackage WHERE PackageID=?";
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        try {
            preparedStatement.setInt(1, packageID);
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

}