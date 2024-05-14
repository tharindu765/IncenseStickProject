package lk.ijse.repository;

import lk.ijse.db.DbConnection;
import lk.ijse.model.PlaceMaterial;

import java.sql.Connection;
import java.sql.SQLException;

public class PlaceMaterialRepo {
    public static boolean placeMaterial(PlaceMaterial pm) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        try {
            boolean isMaterialSaved = MaterialRepo.save(pm.getRawMaterial());

            if (isMaterialSaved) {
                    boolean isSupplierDetailSaved = SupplierDetailRepo.save(pm.getSupplierDetails());
                    if (isSupplierDetailSaved) {
                        connection.commit();
                        return true;
                    }
            }
            connection.rollback();
            return false;
        } catch (Exception e) {
            connection.rollback();
            return false;
        } finally {
            connection.setAutoCommit(true);
        }
    }

}

