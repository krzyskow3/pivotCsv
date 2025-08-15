package pl.pkpik.bilkom.pivotcsv.functions.impl;

import lombok.RequiredArgsConstructor;
import pl.pkpik.bilkom.pivotcsv.csv.Record;
import pl.pkpik.bilkom.pivotcsv.functions.BaseFunction;
import pl.pkpik.bilkom.pivotcsv.functions.FnResult;
import pl.pkpik.bilkom.pivotcsv.functions.params.Param;


@RequiredArgsConstructor
public class FnCase extends BaseFunction {

    private final BaseFunction when;
    private final BaseFunction then;
    private final BaseFunction orElse;

    @Override
    public void apply(Record record, FnResult result, Param[] params) {
        boolean condition = "true".equalsIgnoreCase(when.getValue(record, params));
        if (condition) {
            result.push(then.getValue(record, params));
        } else {
            result.push(orElse.getValue(record, params));
        }
    }
}
