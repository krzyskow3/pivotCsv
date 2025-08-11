package pl.pkpik.bilkom.pivotcsv.functions.impl;

import pl.pkpik.bilkom.pivotcsv.csv.Record;
import pl.pkpik.bilkom.pivotcsv.functions.BaseFunction;
import pl.pkpik.bilkom.pivotcsv.functions.FnResult;
import pl.pkpik.bilkom.pivotcsv.functions.params.Param;

public class FnField extends BaseFunction {

    private final String field;

    public FnField(String field) {
        this.field = field;
    }

    @Override
    public void apply(Record record, FnResult result, Param[] params) {
        result.push(record.getValue(field));
    }
}
