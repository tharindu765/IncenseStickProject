package lk.ijse.repository;

import lk.ijse.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DashbordRepo {
    public static double getCurrentMonthProfit() throws SQLException {
            String query = "SELECT Profit FROM CurrentMonthProfit";
            Connection connection = DbConnection.getInstance().getConnection();
            try {
                 PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return resultSet.getDouble("Profit");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        return 0.0;
        }
}
