package pl.pkpik.bilkom.pivotcsv.functions.impl;

import pl.pkpik.bilkom.pivotcsv.csv.Record;
import pl.pkpik.bilkom.pivotcsv.functions.BaseFunction;
import pl.pkpik.bilkom.pivotcsv.functions.FnResult;
import pl.pkpik.bilkom.pivotcsv.functions.params.Param;

public class FnConst extends BaseFunction {

    private final String value;

    public FnConst(String value) {
        this.value = value;
    }

    @Override
    public void apply(Record record, FnResult result, Param[] params) {
        result.push(value);
    }
}
