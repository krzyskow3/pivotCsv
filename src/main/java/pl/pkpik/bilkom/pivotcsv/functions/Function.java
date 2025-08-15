package pl.pkpik.bilkom.pivotcsv.functions;

import pl.pkpik.bilkom.pivotcsv.csv.Record;
import pl.pkpik.bilkom.pivotcsv.functions.params.Param;

public interface Function {

    String getValue(Record record, Param... params);
    double getFloatValue(Record record, Param... params);
    boolean getBooleanValue(Record record, Param... params);
    int getIntValue(Record record, Param... params);

}
