package pl.pkpik.bilkom.pivotcsv.filters.impl;

import org.apache.commons.lang3.StringUtils;
import pl.pkpik.bilkom.pivotcsv.filters.BaseFilter;
import pl.pkpik.bilkom.pivotcsv.filters.Filter;
import pl.pkpik.bilkom.pivotcsv.functions.BaseFunction;

import java.util.Arrays;

public class FValueIn extends BaseFilter implements Filter {

    public FValueIn(String field, String... values) {
        super(field);
        this.values = Arrays.stream(values).toList();
        this.function = BaseFunction.field(field).in(values);
    }

    @Override
    public String toString() {
        return "Filter: " + field + " IN (" + StringUtils.join(values, ", ") + ")";
    }
}
