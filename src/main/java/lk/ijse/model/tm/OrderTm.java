package lk.ijse.model.tm;

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

}
