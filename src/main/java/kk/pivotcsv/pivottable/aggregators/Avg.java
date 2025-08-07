package kk.pivotcsv.pivottable.aggregators;

import lombok.Data;

@Data
public class Avg implements Aggregator {

    public final String field;
    public final int decimals;


}
