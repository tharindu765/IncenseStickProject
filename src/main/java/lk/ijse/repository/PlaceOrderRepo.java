package lk.ijse.repository;

import lk.ijse.db.DbConnection;
import lk.ijse.model.OrderDetail;
import lk.ijse.model.PlaceOrder;

import java.sql.Connection;
import java.sql.SQLException;

public class PlaceOrderRepo {

    public static boolean placeOrder(PlaceOrder po) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        connection.setAutoCommit(false);
        System.out.println("fuck u");
        try {
            boolean isOrderSaved = OrderRepo.save(po.getOrder());
            System.out.println("fuck u1");
            if (isOrderSaved) {
                boolean isOrderDetailSaved = OrderDetailRepo.save(po.getOdList());
                System.out.println("shit head");
                    if (isOrderDetailSaved) {
                        System.out.println("not here");
                        boolean isSaleSaved = SaleRepo.addOrderSale(po.getSale());
                        if (isSaleSaved) {
                            connection.commit();
                            return true;
                         }
                    }
            }
            System.out.println("rol");
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
