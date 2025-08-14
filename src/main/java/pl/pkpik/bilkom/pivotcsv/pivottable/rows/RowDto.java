package pl.pkpik.bilkom.pivotcsv.pivottable.rows;

import lombok.Data;
import pl.pkpik.bilkom.pivotcsv.csv.Record;
import pl.pkpik.bilkom.pivotcsv.pivottable.columns.ColumnDto;
import pl.pkpik.bilkom.pivotcsv.pivottable.columns.ColumnKey;
import pl.pkpik.bilkom.pivotcsv.pivottable.columns.DataColumnDto;

import java.util.*;
import java.util.stream.Collectors;

@Data
public class RowDto {

    private final RowKey rowKey;
    private final Map<ColumnKey, ColumnDto> columns = new HashMap<>();
    private final Record rowRecord;

    public RowDto(RowKey rowKey) {
        this.rowKey = rowKey;
        this.rowRecord = Record.create(rowKey.getFields(), rowKey.getValues());
    }

    public void groupRecord(Record record, List<String> columnFields) {
        ColumnKey columnKey = new ColumnKey(record, columnFields);
        ColumnDto columnDto = columns.computeIfAbsent(columnKey, k -> new ColumnDto());
        columnDto.addRecord(record);
    }

    public Set<ColumnKey> getColumnKeys() {
        return new HashSet<>(columns.keySet());
    }

    public List<Record> getRecords(List<DataColumnDto> dataColumns) {
        return dataColumns.stream().map(DataColumnDto::getColumnKey)
                .flatMap(key -> getRecords(key).stream())
                .collect(Collectors.toList());
    }

    public List<Record> getRecords(ColumnKey columnKey) {
        return Optional.ofNullable(columns.get(columnKey))
                .orElse(new ColumnDto())
                .getRecords();
    }

    public void calculateRowRecord(List<DataColumnDto> dataColumns) {
        for (DataColumnDto col : dataColumns) {
            List<Record> records = getRecords(col.columnKey);
            String value = col.aggregator.aggregate(records);
            rowRecord.setValue(col.fieldName, value);
        }
    }
}
