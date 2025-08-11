package pl.pkpik.bilkom.pivotcsv.filters.impl;

import org.jetbrains.annotations.NotNull;
import pl.pkpik.bilkom.pivotcsv.filters.BaseFilter;
import pl.pkpik.bilkom.pivotcsv.filters.Filter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static pl.pkpik.bilkom.pivotcsv.functions.FunctionBuilder.field;

public class FDayBetween extends BaseFilter implements Filter {

    private final LocalDate fromDay;
    private final LocalDate toDay;

    public FDayBetween(@NotNull String field, @NotNull LocalDate fromDay, @NotNull LocalDate toDay) {
        super(field);
        this.values = Arrays.asList(fromDay.toString(), toDay.toString());
        this.function = field(field).between(fromDay, toDay);
        this.fromDay = fromDay;
        this.toDay = toDay;
    }

    @Override
    public String toString() {
        return "Filter: " + field + " BETWEEN " + fromDay + " AND " + toDay;
    }
}
