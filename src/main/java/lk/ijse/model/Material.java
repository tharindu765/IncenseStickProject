package lk.ijse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Material {
    private String materialName;
    private int qty;
    private String Name;
    private Date date;

    public Material(String materialName, int qty, String name, Date date, double unitPrice) {
        this.materialName = materialName;
        this.qty = qty;
        Name = name;
        this.date = date;
        UnitPrice = unitPrice;
    }

    private double UnitPrice;
    private double TotalPrice;
}
