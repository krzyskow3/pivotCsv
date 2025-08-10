package pl.pkpik.bilkom.pivotcsv.filters;

import pl.pkpik.bilkom.pivotcsv.csv.Record;
import pl.pkpik.bilkom.pivotcsv.functions.Function;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseFilter implements Filter {

    protected final String field;
    protected List<String> values = new ArrayList<>();
    protected Function function;

    public BaseFilter(String field) {
        this.field = field;
    }

    @Override
    public boolean match(Record rec) {
        return (function != null) && function.getValue(rec).equalsIgnoreCase("true");
    }

    @Override
    public String getField() {
        return field;
    }

    @Override
    public List<String> getValues() {
        return values;
    }

}
