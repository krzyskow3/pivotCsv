package pl.pkpik.bilkom.pivotcsv.pivottable.aggregators;

public class Sum implements Aggregator {

    private final String field;
    private final int decimals;

    public static Sum of(String field, int decimals) {
        return new Sum(field, decimals);
    }

    public static Sum of(String field) {
        return new Sum(field, 0);
    }

    private Sum(String field, int decimals) {
        this.field = field;
        this.decimals = decimals;
    }

    @Override
    public String name() {
        return "sum_" + field;
    }
}
