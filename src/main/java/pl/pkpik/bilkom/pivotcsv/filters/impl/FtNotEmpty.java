package pl.pkpik.bilkom.pivotcsv.filters.impl;

import pl.pkpik.bilkom.pivotcsv.filters.BaseFilter;
import pl.pkpik.bilkom.pivotcsv.filters.Filter;

import static pl.pkpik.bilkom.pivotcsv.functions.FunctionBuilder.field;

public class FtNotEmpty extends BaseFilter implements Filter {

    public FtNotEmpty(String field) {
        super(field);
        this.function = field(field).notEmpty();
    }

    @Override
    public String toString() {
        return "Filter: " + field + " NOT EMPTY";
    }
}
