package pl.pkpik.bilkom.pivotcsv.pivottable;

import lombok.Data;
import pl.pkpik.bilkom.pivotcsv.csv.Csv;
import pl.pkpik.bilkom.pivotcsv.filters.Filter;
import pl.pkpik.bilkom.pivotcsv.pivottable.aggregators.Aggregator;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Data
public class PivotTableBuilder {

    private final PivotTable table;
    private final Set<String> groupFields = new HashSet<>();

    public PivotTableBuilder(Csv source) {
        this.table = new PivotTable(source);
    }

    public PivotTableBuilder withFilter(Filter filter) {
        table.filters.add(filter);
        return this;
    }

    public PivotTableBuilder withRowFields(String... fields) {
        for (String field : fields) {
            if (groupFields.add(field)) {
                table.rowFields.add(field);
            }
        }
        return this;
    }

    public PivotTableBuilder withColumnFields(String... fields) {
        for (String field : fields) {
            if (groupFields.add(field)) {
                table.columnFields.add(field);
            }
        }
        return this;
    }

    public PivotTableBuilder withDataFields(Aggregator... aggregators) {
        table.dataFields.addAll(Arrays.asList(aggregators));
        return this;
    }

    public PivotTableBuilder withRowSummary() {
        table.setRowSummary(true);
        return this;
    }


    public PivotTable build() {
        new PivotTableCalculator(table).calculate();
        return table;
    }

}
