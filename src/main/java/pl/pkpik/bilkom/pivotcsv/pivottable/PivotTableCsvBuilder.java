package pl.pkpik.bilkom.pivotcsv.pivottable;

import pl.pkpik.bilkom.pivotcsv.csv.Csv;
import pl.pkpik.bilkom.pivotcsv.csv.Header;
import pl.pkpik.bilkom.pivotcsv.csv.Record;
import pl.pkpik.bilkom.pivotcsv.filters.Filter;
import pl.pkpik.bilkom.pivotcsv.pivottable.columns.DataColumnDto;
import pl.pkpik.bilkom.pivotcsv.pivottable.rows.RowDto;
import pl.pkpik.bilkom.pivotcsv.pivottable.rows.RowKey;

import java.util.List;
import java.util.stream.Collectors;

class PivotTableCsvBuilder {

    private final PivotTable table;

    PivotTableCsvBuilder(PivotTable table) {
        this.table = table;
    }

    Csv build() {
        Csv csv = new Csv();
        makeCsvHeaders(csv);
        makeCsvFields(csv);
        makeCsvRecords(csv);
        return csv;
    }

    private void makeCsvHeaders(Csv csv) {
        if (!table.filters.isEmpty()) {
            for (Filter filter : table.filters) {
                csv.addHeader(Header.of(filter, table.rowFields.size()));
            }
            csv.addHeader(new Header());
        }
        for (int idx = 0; idx < table.columnFields.size(); idx++) {
            String field = table.columnFields.get(idx);
            csv.addHeader(Header.of(field, table.columnKeys, idx, table.rowFields.size(), table.dataFields.size()));
        }
    }

    private void makeCsvFields(Csv csv) {
        csv.addField("row");
        csv.addFields(table.rowFields);
        csv.addFields(table.dataColumns.stream().map(DataColumnDto::getFieldName).collect(Collectors.toList()));
    }

    private void makeCsvRecords(Csv csv) {
        for (int i = 0; i < table.rowKeys.size(); i++) {
            RowKey rowKey = table.rowKeys.get(i);
            RowDto rowDto = table.rows.get(rowKey);
            Record record = new Record();
            record.setValue("row", Integer.toString(i + 1));
            record.setAllValues(table.rowFields, rowKey.getValues());
            for (DataColumnDto dto : table.dataColumns) {
                List<Record> dataRecords = rowDto.getRecords(dto.columnKey);
                String value = dto.aggregator.aggregate(dataRecords);
                record.setValue(dto.fieldName, value);
            }
            csv.addRecord(record);
        }
    }

}
