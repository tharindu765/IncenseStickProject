package lk.ijse.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SaleTm {
    private int orderID;
    private String pType;
    private Date sDate;
    private String status;
    private int qty;
}
