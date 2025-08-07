package kk.pivotcsv.pivottable.aggregators;

import lombok.Data;

@Data
public class Sum implements Aggregator {

    public final String field;
    public final int decimals;

    public static Sum of(String field, int decimals) {
        return new Sum(field, decimals);
    }

    public static Sum of(String field) {
        return new Sum(field, 0);
    }
}
