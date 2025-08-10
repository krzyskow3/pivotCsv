package pl.pkpik.bilkom.pivotcsv.functions.impl;

import org.apache.commons.lang3.math.NumberUtils;
import pl.pkpik.bilkom.pivotcsv.csv.Record;
import pl.pkpik.bilkom.pivotcsv.functions.BaseFunction;
import pl.pkpik.bilkom.pivotcsv.functions.FResult;

public class FnMultiply extends BaseFunction {

    public FnMultiply(BaseFunction arg) {
        this.arg = arg;
    }

    @Override
    public void apply(Record record, FResult result) {
        double second = NumberUtils.toDouble(result.pop());
        double first = NumberUtils.toDouble(result.pop());
        double value = first * second;
        result.push(String.format("%.4f", value));
    }
}
