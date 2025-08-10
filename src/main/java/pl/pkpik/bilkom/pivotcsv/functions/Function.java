package pl.pkpik.bilkom.pivotcsv.functions;

import pl.pkpik.bilkom.pivotcsv.csv.Record;

public interface Function {

    String F_VALUE = "f(x)";
    String getValue(Record record);

}
