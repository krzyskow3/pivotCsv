package pl.pkpik.bilkom.pivotcsv.csv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CsvData extends ArrayList<HashMap<String, String>> {

        public List<Map<String, Object>> toList() {
            return super.stream().map(this::toMap).collect(Collectors.toList());
        }

        private Map<String, Object> toMap(HashMap<String, String> element) {
            return new HashMap<>(element);
        }

    }