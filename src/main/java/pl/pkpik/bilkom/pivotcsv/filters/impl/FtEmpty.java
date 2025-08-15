package pl.pkpik.bilkom.pivotcsv.filters.impl;

import pl.pkpik.bilkom.pivotcsv.filters.BaseFilter;
import pl.pkpik.bilkom.pivotcsv.filters.Filter;

import static pl.pkpik.bilkom.pivotcsv.functions.FunctionBuilder.field;

public class FtEmpty extends BaseFilter implements Filter {

    public FtEmpty(String field) {
        super(field);
        this.function = field(field).empty();
    }

    @Override
    public String toString() {
        return "Filter: " + field + " EMPTY";
    }
}
