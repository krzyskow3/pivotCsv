package pl.pkpik.bilkom.pivotcsv.functions.impl;

import org.apache.commons.lang3.math.NumberUtils;
import pl.pkpik.bilkom.pivotcsv.csv.Record;
import pl.pkpik.bilkom.pivotcsv.functions.BaseFunction;
import pl.pkpik.bilkom.pivotcsv.functions.FnResult;
import pl.pkpik.bilkom.pivotcsv.functions.Function;
import pl.pkpik.bilkom.pivotcsv.functions.params.Param;

public class FnEquals extends BaseFunction implements Function {

    public FnEquals(BaseFunction arg) {
        this.arg = arg;
    }

    @Override
    public void apply(Record record, FnResult result, Param[] params) {
        String second = result.pop();
        String first = result.pop();
        if (NumberUtils.isCreatable(first) && NumberUtils.isCreatable(second)) {
            double diff = Math.abs(NumberUtils.toDouble(first) - NumberUtils.toDouble(second));
            result.push(Boolean.toString(diff < EPSILON));
        } else {
            result.push(Boolean.toString(first.equals(second)));
        }
    }

}
