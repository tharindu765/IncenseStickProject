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
    private double UnitPrice;
    private double TotalPrice;
    private int RawMaterialId;
    public Material(String materialName, int qty, String name, Date date, double unitPrice) {
        this.materialName = materialName;
        this.qty = qty;
        Name = name;
        this.date = date;
        UnitPrice = unitPrice;
    }

    public Material(String materialName, int qty, String supplierName, Date date, double unitPrice, int rawMaterialId) {
                this.materialName = materialName;
                this.qty = qty;
                this.Name = supplierName;
                this.date = date;
                this.UnitPrice = unitPrice;
                this.RawMaterialId = rawMaterialId;
    }

}
