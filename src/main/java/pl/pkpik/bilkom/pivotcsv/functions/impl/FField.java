package pl.pkpik.bilkom.pivotcsv.functions.impl;

import pl.pkpik.bilkom.pivotcsv.csv.Record;
import pl.pkpik.bilkom.pivotcsv.functions.BaseFunction;
import pl.pkpik.bilkom.pivotcsv.functions.FResult;

public class FField extends BaseFunction {

    private final String field;

    public FField(String field) {
        this.field = field;
    }

    @Override
    public void apply(Record record, FResult result) {
        System.out.println("=> apply: FField");
        result.push(record.getValue(field));
    }
}
