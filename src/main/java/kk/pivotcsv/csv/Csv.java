package kk.pivotcsv.csv;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Data
@NoArgsConstructor
public class Csv {

    private char separator = ';';
    public final List<Header> headers = new ArrayList<>();
    public final List<String> fields = new ArrayList<>();
    public final List<Record> records = new ArrayList<>();

    public Csv(char separator) {
        this.separator = separator;
    }

    public Csv load(File file) throws IOException {
        List<String> lines = FileUtils.readLines(file, "UTF-8");
        for (String line : lines) {
            if (StringUtils.isNotBlank(line)) {
                if (line.startsWith("#")) {
                    List<String> values = splitLine(StringUtils.substring(line,1));
                    headers.add(Header.of(values));
                } else {
                    List<String> values = splitLine(line);
                    if (fields.isEmpty()) {
                        fields.addAll(values);
                    } else {
                        records.add(Record.create(fields, values));
                    }
                }
            }
        }
        return this;
    }

    private List<String> splitLine(String line) {
        return Arrays.stream(StringUtils.splitPreserveAllTokens(line, separator)).collect(Collectors.toList());
    }


}
