package lk.ijse.repository;

import lk.ijse.db.DbConnection;
import lk.ijse.model.Batch;
import lk.ijse.model.Order;
import lk.ijse.model.tm.BatchTm;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BatchRepo {
    public static List<String> getBatchID() throws SQLException {
        String sql = "SELECT BatchID FROM Batch";
        List<String> batchIDs = new ArrayList<>();
        Connection connection = DbConnection.getInstance().getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String batchID = resultSet.getString("BatchID");
                batchIDs.add(batchID);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return batchIDs;
    }

    public static List<Batch> getAll() throws SQLException {
        String sql = "select * from BatchView";
        List<Batch> batche = new ArrayList<>();
        Connection connection = DbConnection.getInstance().getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int batchID = resultSet.getInt("BatchID");
                Date date = resultSet.getDate("Date");
                int qty = resultSet.getInt("TotalQuantity");
                Batch batch = new Batch(batchID, date, qty);
                batche.add(batch);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while fetching batch orders: " + e.getMessage(), e);
        }
        return batche;
    }

    public static boolean save(Batch batch) throws SQLException {
        String sql = "INSERT INTO Batch (BatchID, Date) VALUES (?, ?)";

        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);

        try {
            preparedStatement.setInt(1, batch.getBatchID());
            preparedStatement.setDate(2, batch.getDate());
            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error while saving batch: " + e.getMessage(), e);
        }

    }

    public static boolean update(BatchTm selectedBatch) throws SQLException {
        String sql = "UPDATE Batch SET Date = ? WHERE BatchID = ?";
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        try {
            preparedStatement.setDate(1, selectedBatch.getDate());
            preparedStatement.setInt(2, selectedBatch.getBatchID());

            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Batch> searchBatches(String batchIDText, Date date, String qtyText) throws SQLException {
        String sql = "SELECT * FROM BatchView WHERE Date = ?";
        List<Batch> batches = new ArrayList<>();
        Connection connection = DbConnection.getInstance().getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDate(1, date);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int batchID = resultSet.getInt("BatchID");
                int qty = resultSet.getInt("TotalQuantity");
                Batch batch = new Batch(batchID, date, qty);
                batches.add(batch);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while searching batches by date: " + e.getMessage(), e);
        }
        return batches;
    }
 }
