package pl.pkpik.bilkom.pivotcsv.pivottable.aggregators;

import pl.pkpik.bilkom.pivotcsv.csv.Record;

import java.util.List;

public interface Aggregator {

    String aggregate(List<Record> records);
    String getName();
    String getField();

}
