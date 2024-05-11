package lk.ijse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Batch {
    private int BatchID;
    private Date date;
    private int qty;


    public Batch(int batchID, Date date) {
        BatchID = batchID;
        this.date = date;
    }

}
