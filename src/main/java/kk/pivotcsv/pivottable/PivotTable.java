package kk.pivotcsv.pivottable;

import kk.pivotcsv.pivottable.aggregators.Aggregator;
import kk.pivotcsv.csv.Csv;
import lombok.Data;

import java.util.*;

@Data
public class PivotTable {

    private final Csv source;
    private final List<Filter> filters = new ArrayList<>();
    private final List<String> rowFields = new ArrayList<>();
    private final List<String> columnFields = new ArrayList<>();
    private final Set<String> groupFields = new HashSet<>();
    private final List<Aggregator> dataFields = new ArrayList<>();



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

        return this;
    }
}
