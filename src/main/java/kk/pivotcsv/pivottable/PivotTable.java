package kk.pivotcsv.pivottable;

import kk.pivotcsv.csv.Csv;
import kk.pivotcsv.csv.Record;
import kk.pivotcsv.pivottable.aggregators.Aggregator;
import lombok.Data;

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

    private List<Record> records;

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

    public PivotTable calculate() {
        filterRecords();
        return this;
    }

    private void filterRecords() {
        records = source.getRecords();
        System.out.println("All Records: " + records.size());
        for (Filter filter : filters) {
            records = records.stream().filter(filter::match).collect(Collectors.toList());
            System.out.println("Filter: " + filter.field + " => Records: " + records.size());
        }
    }
}
