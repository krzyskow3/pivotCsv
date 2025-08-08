package pl.pkpik.bilkom.pivotcsv.csv;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import pl.pkpik.bilkom.pivotcsv.filters.Filter;
import pl.pkpik.bilkom.pivotcsv.pivottable.columns.ColumnKey;

import java.util.ArrayList;
import java.util.List;

@Data
public class Header {

    private final List<String> values = new ArrayList<>();

    public static Header of(List<String> values) {
        Header header = new Header();
        header.values.addAll(values);
        return header;
    }

    public static Header of(Filter filter, int offset) {
        Header header = new Header();
        for (int i = 0; i < offset; i++) {
            header.values.add("");
        }
        header.values.add(filter.getField());
        header.values.addAll(filter.getValues());
        return header;
    }

    public static Header of(String field, List<ColumnKey> columnKeys, int idx, int offset, int repeats) {
        Header header = new Header();
        for (int i = 0; i < offset; i++) {
            header.values.add("");
        }
        header.values.add(field);
        List<String> values = columnKeys.stream().map(key -> key.getValue(idx)).toList();
        for (int i = 0; i < repeats; i++) {
            header.values.addAll(values);
        }
        return header;
    }

    public String toCsv(String separator) {
        return StringUtils.join(values, separator);
    }
}
