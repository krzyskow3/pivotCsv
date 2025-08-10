package pl.pkpik.bilkom.pivotcsv.filters.impl;

import org.jetbrains.annotations.NotNull;
import pl.pkpik.bilkom.pivotcsv.filters.BaseFilter;
import pl.pkpik.bilkom.pivotcsv.filters.Filter;
import pl.pkpik.bilkom.pivotcsv.functions.BaseFunction;

import java.time.LocalDate;
import java.util.List;

public class FDayBetween extends BaseFilter implements Filter {

    private final LocalDate fromDay;
    private final LocalDate toDay;

    public FDayBetween(@NotNull String field, @NotNull LocalDate fromDay, @NotNull LocalDate toDay) {
        super(field);
        this.values = List.of(fromDay.toString(), toDay.toString());
        this.function = BaseFunction.field(field).between(fromDay, toDay);
        this.fromDay = fromDay;
        this.toDay = toDay;
    }

    @Override
    public String toString() {
        return "Filter: " + field + " BETWEEN " + fromDay + " AND " + toDay;
    }
}
