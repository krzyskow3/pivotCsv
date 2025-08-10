package pl.pkpik.bilkom.pivotcsv.filters;

import pl.pkpik.bilkom.pivotcsv.filters.impl.FDayBetween;
import pl.pkpik.bilkom.pivotcsv.filters.impl.FValueIn;

import java.time.LocalDate;

public class FilterBuilder {

    private final String field;

    private FilterBuilder(String field) {
        this.field = field;
    }

    public static FilterBuilder field(String field) {
        return new FilterBuilder(field);
    }

    public Filter in(String... values) {
        return new FValueIn(field, values);
    }

    public Filter between(LocalDate fromDay, LocalDate toDay) {
        return new FDayBetween(field, fromDay, toDay);
    }
}
