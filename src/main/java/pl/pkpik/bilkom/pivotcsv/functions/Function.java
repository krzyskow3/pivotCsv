package pl.pkpik.bilkom.pivotcsv.functions;

import pl.pkpik.bilkom.pivotcsv.csv.Record;
import pl.pkpik.bilkom.pivotcsv.functions.params.Param;

public interface Function {

    String getValue(Record record, Param... params);

}
