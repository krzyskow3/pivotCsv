package pl.pkpik.bilkom.pivotcsv.pivottable.columns;

import lombok.Data;
import pl.pkpik.bilkom.pivotcsv.csv.Record;

import java.util.ArrayList;
import java.util.List;

@Data
public class ColumnDto {

    private final List<Record> records = new ArrayList<>();

    public void addRecord(Record record) {
        records.add(record);
    }
}
