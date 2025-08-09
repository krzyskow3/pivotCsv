package pl.pkpik.bilkom.pivotcsv.pivottable.aggregators;

import org.apache.commons.lang3.math.NumberUtils;
import pl.pkpik.bilkom.pivotcsv.pivottable.base.BaseAggregator;

import java.util.List;

public class Sum extends BaseAggregator implements Aggregator {

    private final int decimals;

    public static Sum of(String field, int decimals) { return new Sum(field, decimals); }

    public static Sum of(String field) {
        return new Sum(field, 0);
    }

    private Sum(String field, int decimals) {
        super(field);
        this.decimals = decimals;
    }

    @Override
    public String getName() {
        return "sum";
    }

    @Override
    protected String aggregateValues(List<String> values) {
        double sum = values.stream()
                .map(NumberUtils::toDouble)
                .reduce(0.0, Double::sum);
        String floatFormat = "%." + decimals + "f";
        return (decimals > 0) ? String.format(floatFormat, sum) : String.format("%d", Math.round(sum));
    }

}
