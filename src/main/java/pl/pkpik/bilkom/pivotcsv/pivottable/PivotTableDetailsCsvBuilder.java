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

class PivotTableDetailsCsvBuilder {

    private final PivotTable table;

    PivotTableDetailsCsvBuilder(PivotTable table) {
        this.table = table;
    }

    Csv build() {
        Csv csv = new Csv();
        csv.addFields(table.source.getFields());
        makeCsvRecords(csv);
        return csv;
    }

    private void makeCsvRecords(Csv csv) {
        for (int i = 0; i < table.selectedRowKeys.size(); i++) {
            RowKey rowKey = table.selectedRowKeys.get(i);
            RowDto rowDto = table.rows.get(rowKey);
            rowDto.getRecords(table.dataColumns).forEach(csv::addRecord);
        }
    }

}
