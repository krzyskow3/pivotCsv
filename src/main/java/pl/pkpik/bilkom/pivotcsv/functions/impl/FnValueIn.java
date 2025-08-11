package pl.pkpik.bilkom.pivotcsv.functions.impl;

import pl.pkpik.bilkom.pivotcsv.csv.Record;
import pl.pkpik.bilkom.pivotcsv.functions.BaseFunction;
import pl.pkpik.bilkom.pivotcsv.functions.FnResult;
import pl.pkpik.bilkom.pivotcsv.functions.params.Param;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class FnValueIn extends BaseFunction {

    private Set<String> values = new HashSet<>();

    public FnValueIn(String... values) {
        this.values = Arrays.stream(values).collect(Collectors.toSet());
    }

    @Override
    public void apply(Record record, FnResult result, Param[] params) {
        boolean value = values.contains(result.pop());
        result.push(Boolean.toString(value));
    }
}
