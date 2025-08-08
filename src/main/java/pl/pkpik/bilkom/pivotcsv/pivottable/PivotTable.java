package pl.pkpik.bilkom.pivotcsv.pivottable;

import lombok.Data;
import pl.pkpik.bilkom.pivotcsv.csv.Csv;
import pl.pkpik.bilkom.pivotcsv.csv.Header;
import pl.pkpik.bilkom.pivotcsv.csv.Record;
import pl.pkpik.bilkom.pivotcsv.filters.Filter;
import pl.pkpik.bilkom.pivotcsv.pivottable.aggregators.Aggregator;
import pl.pkpik.bilkom.pivotcsv.pivottable.columns.ColumnKey;
import pl.pkpik.bilkom.pivotcsv.pivottable.rows.RowDto;
import pl.pkpik.bilkom.pivotcsv.pivottable.rows.RowKey;

import java.util.*;
import java.util.stream.Collectors;

@Data
public class PivotTable {

    private final Csv source;
    private final List<Filter> filters = new ArrayList<>();
    private final List<String> rowFields = new ArrayList<>();
    private final List<String> columnFields = new ArrayList<>();
    private final Set<String> groupFields = new HashSet<>();
    private final List<Aggregator> dataFields = new ArrayList<>();

    private final Map<RowKey, RowDto> rows = new HashMap<>();
    private final List<RowKey> rowKeys = new ArrayList<>();
    private final List<ColumnKey> columnKeys = new ArrayList<>();

    public PivotTable withFilter(Filter filter) {
        filters.add(filter);
        return this;
    }

    public PivotTable withRowFields(String... fields) {
        for (String field : fields) {
            if (groupFields.add(field)) {
                rowFields.add(field);
            }
        }
        return this;
    }

    public PivotTable withColumnFields(String... fields) {
        for (String field : fields) {
            if (groupFields.add(field)) {
                columnFields.add(field);
            }
        }
        return this;
    }

    public PivotTable withDataFields(Aggregator... aggregators) {
        dataFields.addAll(Arrays.asList(aggregators));
        return this;
    }

    public Csv build() {
        List<Record> records = filterRecords();
        groupRecords(records);
        return makeCsv();
    }

    private List<Record> filterRecords() {
        List<Record> records = source.getRecords();
        System.out.println("All Records: " + records.size());
        for (Filter filter : filters) {
            records = records.stream().filter(filter::match).collect(Collectors.toList());
            System.out.println(filter + " => Records: " + records.size());
        }
        return records;
    }

    private void groupRecords(List<Record> records) {
        for (Record record : records) {
            RowKey rowKey = new RowKey(record, rowFields);
            RowDto rowDto = rows.computeIfAbsent(rowKey, k -> new RowDto());
            rowDto.groupRecord(record, columnFields);
        }
        rowKeys.addAll(rows.keySet().stream().sorted().toList());
        columnKeys.addAll(rows.values().stream()
                .flatMap(row -> row.getColumnKeys().stream())
                .collect(Collectors.toSet()).stream().sorted()
                .toList());
    }

    private Csv makeCsv() {
        Csv csv = new Csv();
        makeCsvHeaders(csv);
        makeCsvFields(csv);
        makeCsvRecords(csv);
        return csv;
    }

    private void makeCsvHeaders(Csv csv) {
        if (!filters.isEmpty()) {
            for (Filter filter : filters) {
                csv.addHeader(Header.of(filter, rowFields.size()));
            }
            csv.addHeader(new Header());
        }
        for (int idx = 0; idx < columnFields.size(); idx++) {
            String field = columnFields.get(idx);
            csv.addHeader(Header.of(field, columnKeys, idx, rowFields.size(), dataFields.size()));
        }
    }

    private void makeCsvFields(Csv csv) {
        csv.addField("row");
        csv.addFields(rowFields);
        for (Aggregator aggregator : dataFields) {
            for (ColumnKey columnKey : columnKeys) {
                csv.addField(aggregator.name() + "_" + columnKey.name());
            }
        }
    }

    private void makeCsvRecords(Csv csv) {
        for (int i = 0; i < rowKeys.size(); i++) {
            RowKey rowKey = rowKeys.get(i);
            Record record = new Record();
            record.setValue("row", Integer.toString(i + 1));
            record.setAllValues(rowFields, rowKey.getValues());
            csv.addRecord(record);


        }
    }
}
