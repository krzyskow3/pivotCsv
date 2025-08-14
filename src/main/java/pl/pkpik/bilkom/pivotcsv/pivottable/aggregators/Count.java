package pl.pkpik.bilkom.pivotcsv.pivottable.aggregators;

import pl.pkpik.bilkom.pivotcsv.pivottable.base.BaseAggregator;

import java.util.List;

public class Count extends BaseAggregator implements Aggregator {


    public static Count of(String field) {
        return new Count(field);
    }

    private Count(String field) {
        super(field);
    }

    @Override
    public String getName() {
        return "count";
    }

    @Override
    protected String aggregateValues(List<String> values) {
        return Integer.toString(values.size());
    }

}
