package pl.pkpik.bilkom.pivotcsv.functions.impl;

import org.apache.commons.lang3.StringUtils;
import pl.pkpik.bilkom.pivotcsv.csv.Record;
import pl.pkpik.bilkom.pivotcsv.functions.BaseFunction;
import pl.pkpik.bilkom.pivotcsv.functions.FnResult;
import pl.pkpik.bilkom.pivotcsv.functions.params.Param;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class FnNotEmpty extends BaseFunction {

    @Override
    public void apply(Record record, FnResult result, Param[] params) {
        String actual = result.pop();
        result.push(Boolean.toString(StringUtils.isNotEmpty(actual)));
    }
}
