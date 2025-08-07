package kk.pivotcsv;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class PageFilter {

    public final String field;
    public final Set<String> values;

    public PageFilter(String field, String... values) {
        this.field = field;
        this.values = Arrays.stream(values).collect(Collectors.toSet());
    }
}
