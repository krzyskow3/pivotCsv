package pl.pkpik.bilkom.pivotcsv.functions.impl;

import pl.pkpik.bilkom.pivotcsv.csv.Record;
import pl.pkpik.bilkom.pivotcsv.functions.BaseFunction;
import pl.pkpik.bilkom.pivotcsv.functions.FnResult;
import pl.pkpik.bilkom.pivotcsv.functions.params.Param;

public class FnEquals extends BaseFunction {

    private String expected;

    public FnEquals(String value) {
        this.expected = value;
    }

    @Override
    public void apply(Record record, FnResult result, Param[] params) {
        String actual = result.pop();
        boolean match = expected.equals(actual);
        result.push(Boolean.toString(match));
    }
}
