package pl.pkpik.bilkom.pivotcsv.functions.impl;

import org.apache.commons.lang3.math.NumberUtils;
import pl.pkpik.bilkom.pivotcsv.csv.Record;
import pl.pkpik.bilkom.pivotcsv.functions.BaseFunction;
import pl.pkpik.bilkom.pivotcsv.functions.FnResult;
import pl.pkpik.bilkom.pivotcsv.functions.Function;
import pl.pkpik.bilkom.pivotcsv.functions.params.Param;
import pl.pkpik.bilkom.pivotcsv.functions.params.Params;

import static pl.pkpik.bilkom.pivotcsv.functions.params.Params.getDecimals;

public class FnAdd extends BaseFunction implements Function {

    public FnAdd(BaseFunction arg) {
        this.arg = arg;
    }

    @Override
    public void apply(Record record, FnResult result, Param[] params) {
        double second = NumberUtils.toDouble(result.pop());
        double first = NumberUtils.toDouble(result.pop());
        double value = first + second;
        result.push(formatValue(value, getDecimals(params)));
    }

}
