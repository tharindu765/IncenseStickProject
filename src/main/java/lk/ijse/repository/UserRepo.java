package lk.ijse.repository;

import lk.ijse.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepo {
    public static boolean isUserExists() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT COUNT(*) FROM Users";
        try  {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int userCount = resultSet.getInt(1);
                return userCount > 0;
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static boolean registerUser(String username, String password, String ID) throws SQLException {
        if (isUserExists()) {
            return false;
        }
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "INSERT INTO Users (username, password, NIC) VALUES (?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3,ID);
            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static boolean authenticateUser(String username, String password) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT * FROM Users WHERE username = ? AND password = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean truncateUserTable() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "TRUNCATE TABLE Users";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }
    public static boolean checkPassword(Object password) throws SQLException {
        String query = "SELECT COUNT(*) FROM Users WHERE Password = ?";
        Connection connection =DbConnection.getInstance().getConnection();
        try {
             PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, password.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}