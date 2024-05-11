package lk.ijse.model.tm;

import com.jfoenix.controls.JFXButton;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderTm {
    private Date date;
    private String incenseType;
    private String customerName;
    private int qty;
    private double totalPrice;
    private int orderID;
    private JFXButton remove;

    public OrderTm(Date date, String incenseType, String customerName, int qty, double totalPrice, int orderID) {
        this.date = date;
        this.incenseType = incenseType;
        this.customerName = customerName;
        this.qty = qty;
        this.totalPrice = totalPrice;
        this.orderID = orderID;
    }
}
