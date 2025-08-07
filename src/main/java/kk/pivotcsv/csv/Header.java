package kk.pivotcsv.csv;

import lombok.Data;

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
}
