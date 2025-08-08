package pl.pkpik.bilkom.pivotcsv.filters;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import pl.pkpik.bilkom.pivotcsv.csv.Record;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ValueIn implements Filter {

    private final String field;
    private final Set<String> values;

    public ValueIn(@NotNull String field, @NotNull String... values) {
        this.field = field;
        this.values = Arrays.stream(values).collect(Collectors.toSet());
    }

    public boolean match(Record rec) {
        String value = rec.getValue(field);
        return values.contains(value);
    }

    @Override
    public String getField() {
        return field;
    }

    @Override
    public List<String> getValues() {
        return new ArrayList<>(values);
    }

    @Override
    public String toString() {
        return "Filter: " + field + " IN (" + StringUtils.join(values, ", ") + ")";
    }
}
