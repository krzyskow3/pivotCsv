package pl.pkpik.bilkom.pivotcsv.pivottable;

import pl.pkpik.bilkom.pivotcsv.csv.Record;
import pl.pkpik.bilkom.pivotcsv.filters.Filter;
import pl.pkpik.bilkom.pivotcsv.pivottable.aggregators.Aggregator;
import pl.pkpik.bilkom.pivotcsv.pivottable.columns.ColumnKey;
import pl.pkpik.bilkom.pivotcsv.pivottable.columns.DataColumnDto;
import pl.pkpik.bilkom.pivotcsv.pivottable.rows.RowDto;
import pl.pkpik.bilkom.pivotcsv.pivottable.rows.RowKey;

import java.util.List;
import java.util.stream.Collectors;

class PivotTableCalculator {

    private final PivotTable table;
    private List<Record> records;

    PivotTableCalculator(PivotTable table) {
        this.table = table;
    }

    void calculate() {
        filterRecords();
        groupRecords();
        sortKeys();
        prepareDataColumns();
    }

    private void filterRecords() {
        records = table.source.getRecords();
        System.out.println("All Records: " + records.size());
        for (Filter filter : table.filters) {
            records = records.stream().filter(filter::match).collect(Collectors.toList());
            System.out.println(filter + " => Records: " + records.size());
        }
    }

    private void groupRecords() {
        for (Record record : records) {
            RowKey rowKey = new RowKey(record, table.rowFields);
            RowDto rowDto = table.rows.computeIfAbsent(rowKey, k -> new RowDto());
            rowDto.groupRecord(record, table.columnFields);
        }
    }

    private void sortKeys() {
        table.rowKeys.addAll(table.rows.keySet().stream().sorted().toList());
        table.columnKeys.addAll(table.rows.values().stream()
                .flatMap(row -> row.getColumnKeys().stream())
                .collect(Collectors.toSet()).stream().sorted()
                .toList());
    }

    private void prepareDataColumns() {
        for (Aggregator aggregator : table.dataFields) {
            for (ColumnKey columnKey : table.columnKeys) {
                table.dataColumns.add(new DataColumnDto(aggregator, columnKey));
            }
        }
    }

}
