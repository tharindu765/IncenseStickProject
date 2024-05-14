package lk.ijse.model.tm;

import com.jfoenix.controls.JFXButton;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MaterialTm {
    private int RawMaterialId;
    private String SupplierName;
    private String materialName;
    private int qty;
    private String Name;
    private Date date;
    private double UnitPrice;
    private double TotalPrice;
    private JFXButton remove;
    public MaterialTm(String materialName, int qty, String name, Date date, double unitPrice) {
        this.materialName = materialName;
        this.qty = qty;
        Name = name;
        this.date = date;
        UnitPrice = unitPrice;
        this.TotalPrice = unitPrice*qty ;
    }
    public MaterialTm(String materialName, int qty, String supplierName, Date date, double unitPrice, JFXButton remove, int rawMaterialId, String supplierName1) {
        RawMaterialId = rawMaterialId;
        SupplierName = supplierName;
        this.materialName = materialName;
        this.qty = qty;
        Name = supplierName;
        this.date = date;
        UnitPrice = unitPrice;
        this.TotalPrice = unitPrice*qty;
        this.remove = remove;
    }
}
