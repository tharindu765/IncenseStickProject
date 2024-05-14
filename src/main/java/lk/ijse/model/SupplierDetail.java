package lk.ijse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SupplierDetail {
        private int rawMaterialId;
        private String supplierName;
        private Date date;
        private  double unitPrice;
}
