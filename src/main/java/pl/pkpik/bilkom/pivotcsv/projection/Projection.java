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

    private final List<String> fields = new ArrayList<>();

    private final Map<String, String> addFields = new HashMap<>();
    private final Map<String, String> mapFields = new HashMap<>();
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

    public Projection mapField(@NotNull String field, @NotNull String alias) {
        fields.add(alias);
        mapFields.put(field, alias);
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
        for (String field : mapFields.keySet()) {
            String value = rec.getValue(field);
            String alias = mapFields.get(field);
            record.setValue(alias, value);
        }
        return record;
    }

    private boolean filter(Record record) {
        return filters.stream().allMatch(filter -> filter.match(record));
    }
}
