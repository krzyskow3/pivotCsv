package pl.pkpik.bilkom.pivotcsv.filters;

import pl.pkpik.bilkom.pivotcsv.csv.Record;

import java.util.List;

public interface Filter {

    boolean match(Record rec);
    String getField();
    List<String> getValues();

}
