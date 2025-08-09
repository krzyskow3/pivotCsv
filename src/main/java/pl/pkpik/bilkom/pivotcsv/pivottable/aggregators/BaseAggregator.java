package pl.pkpik.bilkom.pivotcsv.pivottable.aggregators;

import pl.pkpik.bilkom.pivotcsv.csv.Record;

import java.util.List;

abstract class BaseAggregator implements Aggregator {

    protected final String field;

    public BaseAggregator(String field) {
        this.field = field;
    }

    @Override
    public String aggregate(List<Record> records) {
        if (records.isEmpty()) {
            return "";
        }
        return aggregateValues(records.stream().map(rec -> rec.getValue(field)).toList());
    }

    public String getField() {
        return field;
    }

    @Override
    public abstract String getName();

    protected abstract String aggregateValues(List<String> values);



}
