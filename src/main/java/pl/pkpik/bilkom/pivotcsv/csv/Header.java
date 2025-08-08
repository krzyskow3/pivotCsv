package pl.pkpik.bilkom.pivotcsv.csv;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

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

    public String toCsv(String separator) {
        return StringUtils.join(values, separator);
    }
}
