package pl.pkpik.bilkom.pivotcsv.pivottable.aggregators;

import pl.pkpik.bilkom.pivotcsv.pivottable.base.BaseAggregator;

import java.util.Comparator;
import java.util.List;

public class Max extends BaseAggregator implements Aggregator {


    public static Max of(String field) {
        return new Max(field);
    }

    private Max(String field) {
        super(field);
    }

    @Override
    public String getName() {
        return "max";
    }

    @Override
    protected String aggregateValues(List<String> values) {
        return values.stream().max(Comparator.naturalOrder()).orElse("");
    }

}
