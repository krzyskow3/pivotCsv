package pl.pkpik.bilkom.pivotcsv.csv.printer;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import pl.pkpik.bilkom.pivotcsv.csv.Csv;
import pl.pkpik.bilkom.pivotcsv.csv.Record;

import java.util.*;

/**
 * Created by Krzysztof Kowalski on 12.08.2025.
 */
public class CsvPrettyPrinter {

    private final Csv csv;

    private final Map<String, Integer> sizeMap = new HashMap<>();

    public CsvPrettyPrinter(Csv csv) {
        this.csv = csv;
    }

    public List<String> print() {
        List<String> lines = new ArrayList<>();
        List<String> fields = csv.getFields();
        List<Record> records = csv.getRecords();
        for (String field : fields) {
            int size = records.stream()
                    .map(r -> r.getValue(field))
                    .mapToInt(String::length)
                    .max().orElse(0);
            sizeMap.put(field, Math.max(size, field.length()));
        }
        lines.add(printFrameLine(fields));
        lines.add(printHeaderLine(fields));
        lines.add(printFrameLine(fields));
        if (!records.isEmpty()) {
            records.forEach(record -> lines.add(printRecordLine(fields, record)));
            lines.add(printFrameLine(fields));
        }
        return lines;
    }

    private String printFrameLine(List<String> fields) {
        StringBuilder line = new StringBuilder();
        line.append("+");
        for (String field : fields) {
            line.append(StringUtils.rightPad("", sizeMap.get(field), '-'));
            line.append("+");
        }
        return line.toString();
    }

    private String printHeaderLine(List<String> fields) {
        StringBuilder line = new StringBuilder();
        line.append("|");
        for (String field : fields) {
            line.append(StringUtils.rightPad(field, sizeMap.get(field), ' '));
            line.append("|");
        }
        return line.toString();
    }

    private String printRecordLine(List<String> fields, Record record) {
        StringBuilder line = new StringBuilder();
        line.append("|");
        for (String field : fields) {
            String value = record.getValue(field);
            if (NumberUtils.isCreatable(value)) {
                line.append(StringUtils.leftPad(value, sizeMap.get(field), ' '));
            } else {
                line.append(StringUtils.rightPad(value, sizeMap.get(field), ' '));
            }
            line.append("|");
        }
        return line.toString();
    }


    @Data
    private static class Column {

        private final String field;
        private final List<String> values;
        private int size;

        Column(String field) {
            this.field = field;
            this.values = new ArrayList<>();
        }

        void addValue(String value) {
            values.add(value);
        }

        void calcSize() {
            size = Math.max(values.stream().mapToInt(String::length).max().orElse(0), field.length());
        }
    }

}
