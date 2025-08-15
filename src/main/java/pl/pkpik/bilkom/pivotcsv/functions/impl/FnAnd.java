package pl.pkpik.bilkom.pivotcsv.functions.impl;

import pl.pkpik.bilkom.pivotcsv.csv.Record;
import pl.pkpik.bilkom.pivotcsv.functions.BaseFunction;
import pl.pkpik.bilkom.pivotcsv.functions.FnResult;
import pl.pkpik.bilkom.pivotcsv.functions.Function;
import pl.pkpik.bilkom.pivotcsv.functions.params.Param;

public class FnAnd extends BaseFunction implements Function {

    public FnAnd(BaseFunction arg) {
        this.arg = arg;
    }

    @Override
    public void apply(Record record, FnResult result, Param[] params) {
        boolean second = "true".equalsIgnoreCase(result.pop());
        boolean first = "true".equalsIgnoreCase(result.pop());
        result.push(Boolean.toString(first && second));
    }

}
