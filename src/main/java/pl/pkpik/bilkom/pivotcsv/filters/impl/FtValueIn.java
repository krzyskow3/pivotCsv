package pl.pkpik.bilkom.pivotcsv.filters.impl;

import org.apache.commons.lang3.StringUtils;
import pl.pkpik.bilkom.pivotcsv.filters.BaseFilter;
import pl.pkpik.bilkom.pivotcsv.filters.Filter;

import java.util.Arrays;

import static pl.pkpik.bilkom.pivotcsv.functions.FunctionBuilder.field;

public class FtValueIn extends BaseFilter implements Filter {

    public FtValueIn(String field, String... values) {
        super(field);
        this.values = Arrays.asList(values);
        this.function = field(field).in(values);
    }

    @Override
    public String toString() {
        return "Filter: " + field + " IN (" + StringUtils.join(values, ", ") + ")";
    }
}
