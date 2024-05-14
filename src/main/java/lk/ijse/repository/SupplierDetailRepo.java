package lk.ijse.repository;

import lk.ijse.db.DbConnection;
import lk.ijse.model.OrderDetail;
import lk.ijse.model.SupplierDetail;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class SupplierDetailRepo {

    public static boolean save(List<SupplierDetail> supplierDetails) throws SQLException {
        for (SupplierDetail sd : supplierDetails) {
            boolean isSaved = save(sd.getRawMaterialId(),sd.getSupplierName(),sd.getDate(),sd.getUnitPrice());
            if (!isSaved) {
                return false;
            }
        }
        return true;
    }

    private static boolean save(int rawMaterialId, String supplierName, Date date, double unitPrice) throws SQLException {
        String insertSupplierDetailSql = "INSERT INTO SupplierDetail (RawMaterialID, SupplierID, Date, Price) VALUES (?, (SELECT SupplierID FROM Supplier WHERE Name = ?), ?, ?)";
        PreparedStatement preparedStatement1 = DbConnection.getInstance().getConnection().prepareStatement(insertSupplierDetailSql);
            preparedStatement1.setInt(1, rawMaterialId);
            preparedStatement1.setString(2, supplierName);
            preparedStatement1.setDate(3, date);
            preparedStatement1.setDouble(4, unitPrice);
            return preparedStatement1.executeUpdate() > 0;

    }
}

