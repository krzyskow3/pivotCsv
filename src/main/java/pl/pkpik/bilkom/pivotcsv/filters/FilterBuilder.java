package pl.pkpik.bilkom.pivotcsv.filters;

import pl.pkpik.bilkom.pivotcsv.filters.impl.FtDayBetween;
import pl.pkpik.bilkom.pivotcsv.filters.impl.FtEmpty;
import pl.pkpik.bilkom.pivotcsv.filters.impl.FtNotEmpty;
import pl.pkpik.bilkom.pivotcsv.filters.impl.FtValueIn;

import java.time.LocalDate;

public class FilterBuilder {

    private final String field;

    private FilterBuilder(String field) {
        this.field = field;
    }

    public static FilterBuilder ftField(String field) {
        return new FilterBuilder(field);
    }

    public Filter in(String... values) {
        return new FtValueIn(field, values);
    }

    public Filter eq(String value) {
        return new FtValueIn(field, value);
    }

    public Filter between(LocalDate fromDay, LocalDate toDay) {
        return new FtDayBetween(field, fromDay, toDay);
    }

    public Filter notEmpty() {
        return new FtNotEmpty(field);
    }

    public Filter empty() {
        return new FtEmpty(field);
    }

}
