package pl.pkpik.bilkom.pivotcsv.csv;

import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import pl.pkpik.bilkom.pivotcsv.csv.dao.CsvData;
import pl.pkpik.bilkom.pivotcsv.csv.printer.CsvPrettyPrinter;
import pl.pkpik.bilkom.pivotcsv.functions.Function;
import pl.pkpik.bilkom.pivotcsv.projection.Projection;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;


public class Csv {

    private static final String DEFAULT_SEPARATOR = ";";
    private static final String DEFAULT_HEADER_PREFIX = "#";

    private final List<Header> headers = new ArrayList<>();
    private final List<String> fields = new ArrayList<>();
    private final List<Record> records = new ArrayList<>();

    public static Csv create(List<Header> headers, List<String> fields, List<Record> records) {
        Csv csv = new Csv();
        csv.headers.addAll(headers);
        csv.fields.addAll(fields);
        csv.records.addAll(records);
        return csv;
    }

    public static Csv create(CsvData data) {
        Set<String> fields = new HashSet<>();
        List<Record> records = new ArrayList<>();
        for (Map<String, String> rec : data) {
            fields.addAll(rec.keySet());
            records.add(Record.create(rec));
        }
        return create(new ArrayList<>(), new ArrayList<>(fields), records);
    }

    public static Csv load(File file) {
        return load(file, DEFAULT_SEPARATOR, DEFAULT_HEADER_PREFIX);
    }

    @SneakyThrows
    public static Csv load(File file, String separator, String headerPrefix) {
        Csv csv = new Csv();
        List<String> lines = FileUtils.readLines(file, "UTF-8");
        for (String line : lines) {
            if (StringUtils.isNotBlank(line)) {
                if (line.startsWith(headerPrefix)) {
                    line = line.replaceFirst(headerPrefix, "");
                    List<String> values = splitLine(line, separator);
                    csv.headers.add(Header.of(values));
                } else {
                    List<String> values = splitLine(line, separator);
                    if (csv.fields.isEmpty()) {
                        csv.fields.addAll(values);
                    } else {
                        csv.records.add(Record.create(csv.fields, values));
                    }
                }
            }
        }
        return csv;
    }

    public Csv save(CsvSaver csvSaver, String name) {
        csvSaver.save(this, name);
        return this;
    }

    public Csv save(File file) {
        return save(file, DEFAULT_SEPARATOR, DEFAULT_HEADER_PREFIX);
    }

    @SneakyThrows
    public Csv save(File file, String separator, String headerPrefix) {
        List<String> lines = new ArrayList<>();
        if (!headers.isEmpty()) {
            headers.forEach(header -> lines.add(headerPrefix + header.toCsv(separator)));
            lines.add("");
        }
        lines.add(StringUtils.join(fields, separator));
        records.forEach(record -> lines.add(record.toCsv(fields, separator)));
        FileUtils.writeLines(file, lines);
        return this;
    }

    public Csv copy() {
        return create(headers, fields, records);
    }

    public int size() { return records.size(); }

    public List<Record> getRecords() { return new ArrayList<>(records); }

    public List<String> getFields() { return new ArrayList<>(fields); }

    public Csv projection(Projection projection) {
        return Csv.create(new ArrayList<>(), projection.getFields(), projection.convertRecords(records));
    }

    public Csv merge(Csv csv) {
        Csv merged = copy();
        merged.mergeFields(csv.fields);
        merged.records.addAll(csv.records);
        return merged;
    }

    public Csv calcField(String field, Function function) {
        fields.add(field);
        for (Record record : records) {
            record.setValue(field, function.getValue(record));
        }
        return this;
    }

    public void addFields(List<String> fields) {
        this.fields.addAll(fields);
    }

    public void addField(String field) {
        this.fields.add(field);
    }

    public void addRecord(Record record) {
        this.records.add(record);
    }

    public void addHeader(Header header) {
        this.headers.add(header);
    }

    public List<String> prettyPrint() {
        return new CsvPrettyPrinter(this).print();
    }

    private static List<String> splitLine(String line, String separator) {
        return Arrays.stream(StringUtils.splitPreserveAllTokens(line, separator)).collect(Collectors.toList());
    }

    private void mergeFields(List<String> fields) {
        Set<String> set = new HashSet<>(this.fields);
        for (String field : fields) {
            if (set.add(field)) {
                this.fields.add(field);
            }
        }
    }

}
