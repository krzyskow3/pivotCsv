package pl.pkpik.bilkom.pivotcsv.functions.impl;

import org.apache.commons.lang3.math.NumberUtils;
import pl.pkpik.bilkom.pivotcsv.csv.Record;
import pl.pkpik.bilkom.pivotcsv.functions.BaseFunction;
import pl.pkpik.bilkom.pivotcsv.functions.FnResult;
import pl.pkpik.bilkom.pivotcsv.functions.params.Param;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FnFloatValueIn extends BaseFunction {

    private List<Double> expected;

    public FnFloatValueIn(double[] values) {
        this.expected = Arrays.stream(values).boxed().collect(Collectors.toList());
    }

    @Override
    public void apply(Record record, FnResult result, Param[] params) {
        double actual = NumberUtils.toDouble(result.pop());
        boolean anyMatch = expected.stream().anyMatch(value -> Math.abs(value - actual) < EPSILON);
        result.push(Boolean.toString(anyMatch));
    }
}
