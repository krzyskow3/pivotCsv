package kk.pivotcsv.csv;

import java.util.HashMap;
import java.util.List;

public class Record extends HashMap<String, String> {

    public static Record create(List<String> fields, List<String> values) {
        Record record = new Record();
        for (int i = 0; i < fields.size(); i++) {
            String field = fields.get(i);
            String value = (i < values.size()) ? values.get(i) : "";
            record.put(field, value);
        }
        return record;
    }
}
