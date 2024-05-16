package lk.ijse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class IncensePackage {
    String description;
    int packageID;
    double unitPrice;
    int qty;

    public IncensePackage( int packageID,String description, int qty) {
        this.description = description;
        this.packageID = packageID;
        this.qty = qty;
    }

}
