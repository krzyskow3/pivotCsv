package kk.pivotcsv.pivottable;

import kk.pivotcsv.csv.Record;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class Filter {

    public final String field;
    public final Set<String> values;

    public Filter(String field, String... values) {
        this.field = field;
        this.values = Arrays.stream(values).collect(Collectors.toSet());
    }

    public boolean match(Record rec) {
        String value = rec.get(field);
        return values.contains(value);
    }
}
