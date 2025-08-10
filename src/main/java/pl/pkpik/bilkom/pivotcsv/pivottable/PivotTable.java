package pl.pkpik.bilkom.pivotcsv.pivottable;

import lombok.Data;
import pl.pkpik.bilkom.pivotcsv.csv.Csv;
import pl.pkpik.bilkom.pivotcsv.filters.Filter;
import pl.pkpik.bilkom.pivotcsv.pivottable.aggregators.Aggregator;
import pl.pkpik.bilkom.pivotcsv.pivottable.columns.ColumnKey;
import pl.pkpik.bilkom.pivotcsv.pivottable.columns.DataColumnDto;
import pl.pkpik.bilkom.pivotcsv.pivottable.rows.RowDto;
import pl.pkpik.bilkom.pivotcsv.pivottable.rows.RowKey;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class PivotTable {

    final Csv source;
    final List<Filter> filters = new ArrayList<>();
    final List<String> rowFields = new ArrayList<>();
    final List<String> columnFields = new ArrayList<>();
    final List<Aggregator> dataFields = new ArrayList<>();
    private boolean rowSummary;


    final Map<RowKey, RowDto> rows = new HashMap<>();
    final List<RowKey> rowKeys = new ArrayList<>();
    final List<ColumnKey> columnKeys = new ArrayList<>();
    final List<DataColumnDto> dataColumns = new ArrayList<>();



    public Csv asCsv() {
        return new PivotTableCsvBuilder(this).build();
    }
}
