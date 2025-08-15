package pl.pkpik.bilkom.pivotcsv.csv.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CsvData extends ArrayList<HashMap<String, String>> {

    public void addRecord(Map<String, String> record) {
        this.add(new HashMap<>(record));
    }

}