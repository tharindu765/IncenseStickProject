package lk.ijse.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MaterialTm {
    private String materialName;
    private int qty;
    private String Name;
    private Date date;
    private double UnitPrice;
    private double TotalPrice;
    public MaterialTm(String materialName, int qty, String name, Date date, double unitPrice) {
        this.materialName = materialName;
        this.qty = qty;
        Name = name;
        this.date = date;
        UnitPrice = unitPrice;
        this.TotalPrice = unitPrice*qty ;
    }

}
