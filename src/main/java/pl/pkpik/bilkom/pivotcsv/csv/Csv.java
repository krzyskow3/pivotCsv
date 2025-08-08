package pl.pkpik.bilkom.pivotcsv.csv;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import pl.pkpik.bilkom.pivotcsv.projection.Projection;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


public class Csv {

    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

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

    public static Csv create(List<Map<String, Object>> data) {
        Set<String> fields = new HashSet<>();
        List<Record> records = new ArrayList<>();
        for (Map<String, Object> rec : data) {
            fields.addAll(rec.keySet());
            records.add(Record.create(rec));
        }
        return create(new ArrayList<>(), new ArrayList<>(fields), records);
    }

    public static Csv load(File file) throws IOException {
        return load(file, DEFAULT_SEPARATOR, DEFAULT_HEADER_PREFIX);
    }

    public static Csv load(File file, String separator, String headerPrefix) throws IOException {
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

    public Csv save(File file) throws IOException {
        return save(file, DEFAULT_SEPARATOR, DEFAULT_HEADER_PREFIX);
    }

    public Csv save(File file, String separator, String headerPrefix) throws IOException {
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

    public Csv projection(Projection projection) {
        return Csv.create(new ArrayList<>(), projection.getFields(), projection.convertRecords(records));
    }

    public Csv merge(Csv csv) {
        Csv merged = copy();
        merged.mergeFields(csv.fields);
        merged.records.addAll(csv.records);
        return merged;
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

    public Csv saveAsJson(File file) throws IOException {
        List<Map<String, String>> data = new ArrayList<>();
        records.forEach(rec -> data.add(rec.getMap()));
        String json = GSON.toJson(data);
        FileUtils.write(file, json, "UTF-8");
        return this;
    }
}
