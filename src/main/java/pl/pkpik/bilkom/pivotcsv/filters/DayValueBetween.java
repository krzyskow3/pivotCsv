package pl.pkpik.bilkom.pivotcsv.filters;

import org.jetbrains.annotations.NotNull;
import pl.pkpik.bilkom.pivotcsv.csv.Record;

import java.time.LocalDate;

public class DayValueBetween implements Filter {

    private final String field;
    private final LocalDate fromDay;
    private final LocalDate toDay;

    public DayValueBetween(@NotNull String field, @NotNull LocalDate fromDay, @NotNull LocalDate toDay) {
        this.field = field;
        this.fromDay = fromDay;
        this.toDay = toDay;
    }

    public boolean match(Record rec) {
        LocalDate day = rec.getLocalDateValue(field);
        return (day != null && !day.isBefore(fromDay) && !day.isAfter(toDay));
    }

    @Override
    public String toString() {
        return "Filter: " + field + " BETWEEN " + fromDay + " AND " + toDay;
    }
}
