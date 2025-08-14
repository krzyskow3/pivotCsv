package pl.pkpik.bilkom.pivotcsv.pivottable;

import lombok.Data;
import pl.pkpik.bilkom.pivotcsv.csv.Csv;
import pl.pkpik.bilkom.pivotcsv.filters.Filter;
import pl.pkpik.bilkom.pivotcsv.pivottable.aggregators.Aggregator;
import pl.pkpik.bilkom.pivotcsv.pivottable.columns.ColumnKey;
import pl.pkpik.bilkom.pivotcsv.pivottable.columns.DataColumnDto;
import pl.pkpik.bilkom.pivotcsv.pivottable.rows.RowDto;
import pl.pkpik.bilkom.pivotcsv.pivottable.rows.RowKey;

import java.util.*;
import java.util.stream.Collectors;

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

    final List<Filter> rowFilters = new ArrayList<>();
    final List<RowKey> selectedRowKeys = new ArrayList<>();

    public PivotTable having(Filter... filters) {
        rowFilters.addAll(Arrays.stream(filters).collect(Collectors.toList()));
        return this;
    }

    public Csv toCsv() {
        filterRowKeys();
        return new PivotTableCsvBuilder(this).build();
    }

    public Csv toDetailsCsv() {
        filterRowKeys();
        return new PivotTableDetailsCsvBuilder(this).build();
    }

    private void filterRowKeys() {
        selectedRowKeys.clear();
        List<RowKey> selected = new ArrayList<>(rowKeys);
        for (Filter filter : rowFilters) {
            selected = selected.stream()
                    .filter(key -> filter.match(rows.get(key).getRowRecord()))
                    .collect(Collectors.toList());
        }
        selectedRowKeys.addAll(selected);
    }
}
