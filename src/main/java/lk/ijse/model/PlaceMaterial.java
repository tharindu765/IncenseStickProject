package lk.ijse.model;

import lk.ijse.model.Material;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PlaceMaterial{
    private Material RawMaterial;
    private List<SupplierDetail> supplierDetails;
}
