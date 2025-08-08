package pl.pkpik.bilkom.pivotcsv.projection;

import lombok.Data;
import org.jetbrains.annotations.NotNull;
import pl.pkpik.bilkom.pivotcsv.csv.Record;
import pl.pkpik.bilkom.pivotcsv.filters.Filter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class Projection {

    // List of projection fields
    private final List<String> fields = new ArrayList<>();

    // Map: projection field => default value
    private final Map<String, String> addFields = new HashMap<>();

    // Map: source field => projection field
    private final Map<String, String> mapFields = new HashMap<>();

    // List of projection field filters
    private final List<Filter> filters = new ArrayList<>();

    public Projection addField(@NotNull String field, String defValue) {
        fields.add(field);
        addFields.put(field, defValue);
        return this;
    }

    public Projection mapField(@NotNull String field) {
        fields.add(field);
        mapFields.put(field, field);
        return this;
    }

    public Projection mapField(@NotNull String field, @NotNull String srcField) {
        fields.add(field);
        mapFields.put(srcField, field);
        return this;
    }

    public Projection addFilter(Filter filter) {
        filters.add(filter);
        return this;
    }

    public List<Record> convertRecords(List<Record> records) {
        return records.stream()
                .map(this::convert)
                .filter(this::filter)
                .collect(Collectors.toList());
    }

    private Record convert(Record rec) {
        Record record = new Record();
        record.setAllValues(addFields);
        for (String srcField : mapFields.keySet()) {
            String value = rec.getValue(srcField);
            String field = mapFields.get(srcField);
            record.setValue(field, value);
        }
        return record;
    }

    private boolean filter(Record record) {
        return filters.stream().allMatch(filter -> filter.match(record));
    }
}
