package pl.pkpik.bilkom.pivotcsv.functions.impl;

import pl.pkpik.bilkom.pivotcsv.csv.Record;
import pl.pkpik.bilkom.pivotcsv.functions.BaseFunction;
import pl.pkpik.bilkom.pivotcsv.functions.FResult;

public class FnField extends BaseFunction {

    private final String field;

    public FnField(String field) {
        this.field = field;
    }

    @Override
    public void apply(Record record, FResult result) {
        result.push(record.getValue(field));
    }
}
