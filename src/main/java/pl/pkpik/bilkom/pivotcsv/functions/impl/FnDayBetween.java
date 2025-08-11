package pl.pkpik.bilkom.pivotcsv.functions.impl;

import lombok.RequiredArgsConstructor;
import pl.pkpik.bilkom.pivotcsv.csv.Record;
import pl.pkpik.bilkom.pivotcsv.functions.BaseFunction;
import pl.pkpik.bilkom.pivotcsv.functions.FnResult;
import pl.pkpik.bilkom.pivotcsv.functions.params.Param;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@RequiredArgsConstructor
public class FnDayBetween extends BaseFunction {

    private final LocalDate fromDay;
    private final LocalDate toDay;

    @Override
    public void apply(Record record, FnResult result, Param[] params) {
        LocalDate day = localDateValue(result.pop());
        boolean value = (day != null) && !day.isBefore(fromDay) && !day.isAfter(toDay);
        result.push(Boolean.toString(value));
    }

    private LocalDate localDateValue(String value) {
        try {
            return LocalDate.parse(value);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

}
