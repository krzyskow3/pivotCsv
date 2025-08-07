package kk.pivotcsv;

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
    private final List<String> fields = new ArrayList<>();
    private final List<Record> records = new ArrayList<>();

    public Csv(char separator) {
        this.separator = separator;
    }

    public Csv load(File file) throws IOException {
        List<String> lines = FileUtils.readLines(file, "UTF-8");
        for (String line : lines) {
            if (StringUtils.isNotBlank(line) && !line.startsWith("#")) {
                List<String> values = Arrays.stream(StringUtils.splitPreserveAllTokens(lines.get(0), separator))
                        .collect(Collectors.toList());
                if (fields.isEmpty()) {
                    fields.addAll(values);
                } else {
                    records.add(Record.create(fields, values));
                }
            }
        }
        return this;
    }


}
