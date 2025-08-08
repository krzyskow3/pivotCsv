package pl.pkpik.bilkom.pivotcsv.filters;

import pl.pkpik.bilkom.pivotcsv.csv.Record;

public interface Filter {

    boolean match(Record rec);

}
