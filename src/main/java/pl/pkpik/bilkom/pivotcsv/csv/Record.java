package pl.pkpik.bilkom.pivotcsv.csv;

import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class Record {

    private final HashMap<String, String> map = new HashMap<>();

    public static final String NULL_TAG = "<NULL>";

    public static Record create(List<String> fields, List<String> values) {
        Record record = new Record();
        for (int i = 0; i < fields.size(); i++) {
            String field = fields.get(i);
            String value = (i < values.size()) ? values.get(i) : "";
            record.map.put(field, value);
        }
        return record;
    }

    public static Record create(Map<String, Object> rec) {
        List<String> fields = new ArrayList<>();
        List<String> values = new ArrayList<>();
        for (String key : rec.keySet()) {
            fields.add(key);
            Object value = rec.get(key);

            values.add(value == null ? null : value.toString());
        }
        return Record.create(fields, values);
    }

    public Record copy() {
        Record record = new Record();
        record.map.putAll(map);
        return record;
    }

    public String toCsv(List<String> fields, String separator) {
        return fields.stream()
                .map(this::getValue)
                .collect(Collectors.joining(separator));
    }

    public String getValue(String field) {
        String value = map.get(field);
        return value == null ? NULL_TAG : value;
    }

    public Record setAllValues(Map<String, String> valuesMap) {
        map.putAll(valuesMap);
        return this;
    }

    public Record setAllValues(List<String> fields, List<String> values) {
        for (int i = 0; i < fields.size(); i++) {
            if (i < values.size()) {
                map.put(fields.get(i), values.get(i));
            }
        }
        return this;
    }

    public Record setValue(String field, String value) {
        map.put(field, value);
        return this;
    }

    public boolean hasField(String field) {
        return map.containsKey(field);
    }

    public LocalDate getLocalDateValue(String field) {
        try {
            return LocalDate.parse(getValue(field));
        } catch (DateTimeParseException e) {
            return null;
        }
    }
}
