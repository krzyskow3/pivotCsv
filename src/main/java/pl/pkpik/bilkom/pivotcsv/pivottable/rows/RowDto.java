package pl.pkpik.bilkom.pivotcsv.pivottable.rows;

import lombok.Data;
import pl.pkpik.bilkom.pivotcsv.csv.Record;
import pl.pkpik.bilkom.pivotcsv.pivottable.columns.ColumnDto;
import pl.pkpik.bilkom.pivotcsv.pivottable.columns.ColumnKey;

import java.util.*;

@Data
public class RowDto {

    private final Map<ColumnKey, ColumnDto> columns = new HashMap<>();

    public void groupRecord(Record record, List<String> columnFields) {
        ColumnKey columnKey = new ColumnKey(record, columnFields);
        ColumnDto columnDto = columns.computeIfAbsent(columnKey, k -> new ColumnDto());
        columnDto.addRecord(record);
    }

    public Set<ColumnKey> getColumnKeys() {
        return new HashSet<>(columns.keySet());
    }
}
