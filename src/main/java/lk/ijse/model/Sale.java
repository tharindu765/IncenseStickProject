package lk.ijse.model;

import javafx.scene.control.TextField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Sale {
    private int transactionID;
    private int qty;
    private Date sDate;
    private int orderID;
    private Date oDate;
    private String customerName;
    private String status;
    private String pType;

    public Sale(String transactionID, Date sDate, int qty, int orderID, String status) {
        this.transactionID = Integer.parseInt(transactionID);
        this.qty = qty;
        this.sDate = sDate;
        this.orderID = orderID;
        this.status = status;
    }
}

