package pl.pkpik.bilkom.pivotcsv.functions.impl;

import pl.pkpik.bilkom.pivotcsv.csv.Record;
import pl.pkpik.bilkom.pivotcsv.functions.BaseFunction;
import pl.pkpik.bilkom.pivotcsv.functions.FnResult;
import pl.pkpik.bilkom.pivotcsv.functions.Function;
import pl.pkpik.bilkom.pivotcsv.functions.params.Param;

public class FnNot extends BaseFunction implements Function {

    public FnNot(BaseFunction arg) {
        this.arg = arg;
    }

    @Override
    public void apply(Record record, FnResult result, Param[] params) {
        boolean value = "true".equalsIgnoreCase(result.pop());
        result.push(Boolean.toString(!value));
    }

}
