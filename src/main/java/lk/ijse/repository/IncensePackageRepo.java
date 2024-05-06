package lk.ijse.repository;

import lk.ijse.db.DbConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IncensePackageRepo {

    public static boolean exitPackage(String incenseType) throws SQLException {
        String sql = "SELECT * FROM IncensePackage WHERE Description = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setString(1, incenseType); // Set the parameter value for the Description column
        ResultSet resultSet = pstm.executeQuery();
        return resultSet.next(); // Return true if a record is found, false otherwise
    }

    public static List<String> getIncenseType() throws SQLException {
        String sql = "select Description from IncensePackage";
        PreparedStatement pstm =DbConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();
        List<String> incenseTypeList = new ArrayList<>();
        while (resultSet.next()){
            String incenseType = resultSet.getString(1);
            incenseTypeList.add(incenseType);
        }
        return incenseTypeList;
    }
    public static int getPackageId(String incenseType) throws SQLException {
        String sql = "SELECT PackageID FROM IncensePackage WHERE Description = ?";
           PreparedStatement pst = DbConnection.getInstance().getConnection().prepareStatement(sql);

            pst.setString(1, incenseType);
            ResultSet rst = pst.executeQuery();

            if (rst.next()) {
                return rst.getInt("PackageID");
            }
        return -1;
    }
}
